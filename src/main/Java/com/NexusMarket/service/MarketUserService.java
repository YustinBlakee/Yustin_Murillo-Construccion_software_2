package com.NexusMarket.service;

import com.NexusMarket.model.MarketUser;
import com.NexusMarket.repository.MarketUserRepository;
import com.NexusMarket.exception.BadRequestException;
import com.NexusMarket.exception.ResourceConflictException;
import com.NexusMarket.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MarketUserService {

    private final MarketUserRepository userRepository;

    public MarketUserService(MarketUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MarketUser create(MarketUser user) {
        validate(user);
        String email = user.getEmail().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ResourceConflictException("Ya existe un usuario con ese correo");
        }
        user.setName(user.getName().trim());
        user.setEmail(email);
        return userRepository.save(user);
    }

    public MarketUser update(Long id, MarketUser changes) {
        MarketUser user = getById(id);
        validate(changes);
        String email = changes.getEmail().trim().toLowerCase(Locale.ROOT);
        if (!user.getEmail().equalsIgnoreCase(email) && userRepository.existsByEmailIgnoreCase(email)) {
            throw new ResourceConflictException("Ya existe un usuario con ese correo");
        }
        user.setName(changes.getName().trim());
        user.setEmail(email);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public MarketUser getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public List<MarketUser> getAll() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        userRepository.delete(getById(id));
    }

    private void validate(MarketUser user) {
        if (user == null || user.getName() == null || user.getName().isBlank()) {
            throw new BadRequestException("El nombre del usuario es obligatorio");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new BadRequestException("El correo del usuario no es válido");
        }
    }
}