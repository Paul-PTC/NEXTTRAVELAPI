package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Cliente;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ClienteRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServices {
    @Autowired
    private ClienteRepository repo;

    @Autowired
    private UserRepository usrep;
}
