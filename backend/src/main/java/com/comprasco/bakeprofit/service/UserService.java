package com.comprasco.bakeprofit.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.comprasco.bakeprofit.entity.User;
import com.comprasco.bakeprofit.entity.Role;
import com.comprasco.bakeprofit.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de gestión de usuarios.
 * Maneja consultas de lectura, registro y verificación de credenciales.
 */

@Service
@Transactional(readOnly = true) // Toda la clase es lectura por defecto
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Inyección por constructor. Correcto.
     * 
     * NOTA: El orden de asignación no coincide con el orden de parámetros.
     * Funciona, pero confunde al lector. Mantené coherencia.
     */
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * ============================================================
     * SECCION: CONSULTAS (Read-Only)
     * Heredan @Transactional(readOnly = true) de la clase
     * ============================================================
     */

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con email: " + email));
    }

    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public List<User> findByActiveTrue() {
        return userRepository.findByActiveTrue();
    }

    public List<User> findByActiveFalse() {
        return userRepository.findByActiveFalse();
    }

    /*
     * ============================================================
     * SECCION: REGISTRO / AUTENTICACIÓN
     * ============================================================
     */

    /**
     * Registra un usuario nuevo hasheando la password con BCrypt.
     * 
     * @Transactional sin readOnly sobreescribe el de la clase y permite escritura.
     * 
     *                NOTA DE MEJORA: RuntimeException genérico es difícil de
     *                capturar en el controller.
     *                Considerá una excepción propia como
     *                EmailAlreadyExistsException.
     */
    @Transactional
    public User register(String name, String email, String rawPassword, Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El email ya esta registrado: " + email);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);

        return userRepository.save(user);
    }

    /**
     * Verifica email + password contra la base de datos.
     * 
     * NOTA DE SEGURIDAD: Si el email no existe, lanzás EntityNotFoundException.
     * Eso le dice a un atacante si el email está registrado o no.
     * Para login, devolvé siempre el mismo mensaje genérico sin importar qué falló.
     */
    public boolean verifyCredentials(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Credenciales invalidos"));

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
    
}