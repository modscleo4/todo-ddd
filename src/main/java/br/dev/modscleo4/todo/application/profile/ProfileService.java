package br.dev.modscleo4.todo.application.profile;

import br.dev.modscleo4.todo.domain.profile.Profile;
import br.dev.modscleo4.todo.domain.profile.ProfileAlreadyExistsException;
import br.dev.modscleo4.todo.domain.profile.ProfileNotFoundException;
import br.dev.modscleo4.todo.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    private final ProfileRepository repository;

    @Transactional
    public Profile create(User user, String name, String cpf, Date birthDate) {
        if (user.getProfile() != null) { throw new ProfileAlreadyExistsException(); }

        var profile = new Profile();
        profile.setName(name);
        profile.setCpf(cpf);
        profile.setBirthDate(birthDate);
        profile.setUser(user);

        return repository.save(profile);
    }

    @Transactional
    public Profile update(Profile profile, String name) {
        if (profile == null) { throw new ProfileNotFoundException(); }

        if (name != null) profile.setName(name);

        return repository.save(profile);
    }
}
