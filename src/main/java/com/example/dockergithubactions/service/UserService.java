package com.example.dockergithubactions.service;

import com.example.dockergithubactions.controller.LoginRequest;
import com.example.dockergithubactions.controller.RegisterRequest;
import com.example.dockergithubactions.model.User;
import com.example.dockergithubactions.repository.UserRepository;
import com.example.dockergithubactions.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final JwtUtils jwtUtils;
	private final PasswordEncoder encoder;

	public UserService(final UserRepository userRepository, final PasswordEncoder encoder, final JwtUtils jwtUtils) {
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	public void register(final RegisterRequest req) {
		final User user = new User();
		user.setUsername(req.getUsername());
		user.setPassword(encoder.encode(req.getPassword()));
		user.setEmail(req.getEmail());
		user.setRole(req.getRole());
		userRepository.save(user);
	}

	public String login(final LoginRequest req) {
		final User user = userRepository.findByUsername(req.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!encoder.matches(req.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid password");
		}

		return jwtUtils.generateJwtToken(user.getUsername());
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(final Long id) {
		return userRepository.findById(id);
	}

	public User createUser(final User user) {
		return userRepository.save(user);
	}

	public User updateUser(final Long id, final User userDetails) {
		return userRepository.findById(id).map(user -> {
			user.setUsername(userDetails.getUsername());
			user.setEmail(userDetails.getEmail());
			user.setPassword(userDetails.getPassword());
			user.setRole(userDetails.getRole());
			return userRepository.save(user);
		}).orElseThrow(() -> new RuntimeException("User not found"));
	}

	public void deleteUser(final Long id) {
		userRepository.deleteById(id);
	}
}
