package com.goggles.user_service.user.infrastructure.repository;

import com.goggles.user_service.user.domain.entity.User;
import com.goggles.user_service.user.domain.entity.UserId;
import com.goggles.user_service.user.domain.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

  private final SpringDataUserRepository repository;

  @Override
  public User save(User user) {
    return repository.save(user);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return repository.findByEmail_Email(email);
  }

  @Override
  public boolean existsByEmail(String email) {
    return repository.existsByEmail_Email(email);
  }

  @Override
  public Optional<User> findById(UUID id) {
    return repository.findById(UserId.of(id));
  }

  @Override
  public boolean existsByNickName(String nickName) {
    return repository.existsByNickName_NickName(nickName);
  }

  @Override
  public boolean existsByPhoneNumber(String phoneNumber) {
    return repository.existsByPhoneNumber_PhoneNumber(phoneNumber);
  }
}
