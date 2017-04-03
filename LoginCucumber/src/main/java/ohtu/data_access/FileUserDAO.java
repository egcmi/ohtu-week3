package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import ohtu.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileUserDAO implements UserDao {
    ArrayList<User> users;
    File file;
    
    @Autowired
    public FileUserDAO(String filename){
        try {
            this.file = new File(filename);
            users = readFromFile();
        } catch (FileNotFoundException ex) {
            System.out.println("File " + filename + " not found.");
        }
    }

    @Override
    public List<User> listAll() {
        
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void add(User user) {
        users.add(user);
        writeToFile(user);
    }
    
    private ArrayList<User> readFromFile() throws FileNotFoundException{
        ArrayList<User> list = new ArrayList<User>();
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()){
            String username = scan.next();
            String password = scan.next();
            list.add(new User(username, password));
        }
        
        return list;
    }
    
    private void writeToFile(User user){
        FileWriter write;
        try {
            write = new FileWriter(file, true);
            write.write(user.getUsername() + " " + user.getPassword());
            write.close();
        } catch (IOException ex) {
            System.out.println("Could not write to file " + file.getAbsolutePath());
        }
    } 
}