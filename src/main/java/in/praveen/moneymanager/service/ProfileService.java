package in.praveen.moneymanager.service;

import in.praveen.moneymanager.Mapper.ProfileMapper;
import in.praveen.moneymanager.dto.AuthDTO;
import in.praveen.moneymanager.dto.ProfileDTO;
import in.praveen.moneymanager.entity.ProfileEntity;
import in.praveen.moneymanager.repository.ProfileRepository;
import in.praveen.moneymanager.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ProfileMapper mapper;

    @Value("${app.activation.url}")
    private String activationUrl;


    //registration
    public ProfileDTO registerProfile(ProfileDTO profileDTO){

        ProfileEntity newProfile =mapper.toEntity(profileDTO);

        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile.setPassword(passwordEncoder.encode(newProfile.getPassword()));
        newProfile =  profileRepository.save(newProfile);

        //send activation email
        String activationLink = activationUrl+"/api/v1.0/activate?token="+newProfile.getActivationToken();
        String subject = "Activate your Money Manger Account";
        String body = "Click on the following link to activate your account " + activationLink;

        emailService.sendEmail(newProfile.getEmail(),subject,body);

       return mapper.toDto(newProfile);

    }



    //profile activation
    public boolean activateProfile(String activationToken){
        return profileRepository
                .findByActivationToken(activationToken)
                .map(profile ->
        {
            profile.setIsActive(true);
            profileRepository.save(profile);
            return true;
        }).orElse(false);
    }



    //before login checking account activated
    public boolean isAccountActive(String email){
        return  profileRepository.findByEmail(email)
                .map(ProfileEntity::getIsActive)
                .orElse(false);
    }

    public ProfileEntity getCurrentProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

     return profileRepository
             .findByEmail(authentication.getName())
             .orElseThrow(()-> new UsernameNotFoundException("Profile not found with email: "+authentication.getName()));
    }

    public ProfileDTO getPublicProfile(String email){
        ProfileEntity currentUser =null;
        if(email==null){
           currentUser = getCurrentProfile();
        }else{
            currentUser=   profileRepository.findByEmail(email)
                    .orElseThrow(()->new UsernameNotFoundException("Profile not found with email: "+email));
        }

        return mapper.toDto(currentUser) ;
    }

    public Map<String,Object> authenticateAndGenerateToken(AuthDTO authDTO){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(),authDTO.getPassword()));
            String token = jwtUtil.generateToken(authDTO.getEmail());
            return Map.of(
                    "token",token,
                    "user",getPublicProfile(authDTO.getEmail())
            );

        } catch (RuntimeException e) {
            System.out.println(e.toString());
            throw new RuntimeException("Invalid email or password");
        }
    }
}
