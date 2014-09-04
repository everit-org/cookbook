package org.everit.cookbook.internal;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.everit.cookbook.UserService;
import org.everit.cookbook.dto.UserDTO;

@Component
@Service
public class UserServiceComponent implements UserService {

    private final Map<Long, UserDTO> storage = new ConcurrentHashMap<Long, UserDTO>();
    
    private final AtomicLong lastGeneratedUserId = new AtomicLong();

    @Override
    public long createUser(String firstName, String lastName) {        
        long userId = lastGeneratedUserId.get();
        UserDTO userData = new UserDTO(userId, firstName, lastName);
        storage.put(userId, userData);
        return userId;
    }

    @Override
    public UserDTO getUserById(long userId) {
        return storage.get(userId);
    }

}
