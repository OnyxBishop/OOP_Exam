package utils;

import model.User;

import java.io.*;
import java.util.Map;

public class DataStorage {
    public void saveUsers(Map<String, User> users, String fileName) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(users);
        }
    }

    public Map<String, User> loadUsers(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<String, User>) inputStream.readObject();
        }
    }
}
