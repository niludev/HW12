package ir.maktabsharif.service;


import ir.maktabsharif.models.User;
import ir.maktabsharif.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;
    private User loggedInUser;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String username, String password) {
        if(userRepository.existsByUsername(username)) {
//            throw new RuntimeException("Username already exists");
            return null;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);

        return user;
    }

    public User login(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
//            System.out.println("Login failed (username not found)");
            return null;
        }

        if (!user.getPassword().equals(password)) {

//            System.out.println("Login failed (wrong password)");
            return null;
        }

//        System.out.println("User with the username " + username + " was successfully logged in");
        loggedInUser = user;

        return user;
    }

    public void logOut () {

        if (loggedInUser == null) {
//            System.out.println("No user is currently logged in");
            return;
        }

//        System.out.println("User with the username " + loggedInUser.getUsername() + " logged out");
        loggedInUser = null;
    }
}




