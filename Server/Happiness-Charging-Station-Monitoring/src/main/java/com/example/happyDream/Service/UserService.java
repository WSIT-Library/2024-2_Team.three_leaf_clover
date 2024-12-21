package com.example.happyDream.Service;

import com.example.happyDream.DTO.UserDTO;
import com.example.happyDream.Entity.UserEntity;
import com.example.happyDream.Repository.UserRepository;
import com.example.happyDream.Util.Converter;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> userSelectAll() {
        List<UserEntity> entityList = this.userRepository.findAll();
        return Converter.EntityListToDtoList(entityList, UserEntity::toDTO);
    }

    public void userDeleteAll() {
        this.userRepository.deleteAll();
    }

    // 회원가입
    public void userInsert(String username, String password, byte userType) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(password);
        System.out.println("암호화된 비밀번호: " + encryptedPassword);

        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .password(encryptedPassword)
                .userType(userType)
                .createdAt(LocalDateTime.now())
                .modifiedAt(null)
                .deletedYn(false)
                .deletedAt(null)
                .build();

        this.userRepository.save(userDTO.toEntity());
    }

    public UserDTO userSelect(Integer id) {
        Optional<UserEntity> entity = this.userRepository.findById(id);
        if(entity.isEmpty()){
            throw new EntityNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return entity.get().toDTO();
    }

    public void userDelete(Integer id) {
        this.userRepository.deleteById(id);
    }

    public boolean validateUser(String username, String rawPassword) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        String storedPassword = userEntity.getPassword();

        return passwordEncoder.matches(rawPassword, storedPassword);
    }

    public Optional<UserEntity> findUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean idCheck(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // UserDetailsService 메소드 구현
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(Collections.singleton(() -> "ROLE_USER")) // 필요한 역할 설정
                .build();
    }
}
