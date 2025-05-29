package com.example.user_service.Services;



import com.example.user_service.Entities.RefreshToken;
import com.example.user_service.Repositories.RefreshTokenRepo;
import com.example.user_service.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
  @Value("${app.jwtRefreshExpirationMs}") // Récupère délai d’expiration des Refresh Tokens depuis le fichier application.properties
  private Long refreshTokenDurationMs;

  @Autowired
  private RefreshTokenRepo refreshTokenRepository;

  @Autowired
  private UserRepo userRepository;


  //Trouver un Refresh Token dans la base de données
  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }



  //Créer un Refresh Token pour un utilisateur
  public RefreshToken createRefreshToken(Long userId) {
    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(userRepository.findById(userId).get()); // Associe le token à l'utilisateur
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs)); // Définit la date d'expiration
    refreshToken.setToken(UUID.randomUUID().toString()); // Génère un token unique

    refreshToken = refreshTokenRepository.save(refreshToken); // Sauvegarde en base de données
    return refreshToken;
  }




  //Vérifier si un Refresh Token est expiré
  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) { //< 0 → Le token est expiré.
      refreshTokenRepository.delete(token);
     // throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
    }

    return token;
  }



  //Supprime tous les tokens d'un utilisateur donné.
  //
  //Retourne le nombre de tokens supprimés.
  @Transactional
  public int deleteByUserId(Long userId) {
    return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
  }
}
