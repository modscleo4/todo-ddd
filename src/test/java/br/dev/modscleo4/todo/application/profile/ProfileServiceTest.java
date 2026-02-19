package br.dev.modscleo4.todo.application.profile;

import br.dev.modscleo4.todo.TestUtils;
import br.dev.modscleo4.todo.domain.profile.Profile;
import br.dev.modscleo4.todo.domain.profile.ProfileAlreadyExistsException;
import br.dev.modscleo4.todo.domain.profile.ProfileNotFoundException;
import br.dev.modscleo4.todo.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {
    @Mock
    private ProfileRepository profileRepository;
    @InjectMocks
    private ProfileService profileService;

    @Test
    void create_shouldThrowWhenUserAlreadyHasProfile() {
        var user = new User();
        user.setProfile(new Profile());

        assertThrows(ProfileAlreadyExistsException.class, () -> {
            profileService.create(user, "Name", "12345678901", new Date());
        });
    }

    @Test
    void create_shouldPopulateAndSaveProfile() {
        var user = new User();
        user.setId(java.util.UUID.randomUUID());

        ArgumentCaptor<Profile> captor = ArgumentCaptor.forClass(Profile.class);
        TestUtils.mockSave(profileRepository);

        var created = profileService.create(user, "Name", "12345678901", new Date());

        verify(profileRepository, times(1)).save(captor.capture());
        var saved = captor.getValue();

        assertEquals("Name", saved.getName());
        assertEquals("12345678901", saved.getCpf());
        assertSame(user, saved.getUser());
        assertSame(saved, created);
    }

    @Test
    void update_shouldThrowWhenProfileNull() {
        assertThrows(ProfileNotFoundException.class, () -> profileService.update(null, "Name"));
    }

    @Test
    void update_shouldModifyAndSave() {
        var profile = new Profile();
        profile.setName("Old");

        when(profileRepository.save(profile)).thenReturn(profile);

        var updated = profileService.update(profile, "New");

        verify(profileRepository, times(1)).save(profile);
        assertEquals("New", updated.getName());
    }
}

