package com.phonebook.repository;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.phonebook.model.User;
import com.phonebook.service.SecurityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@Repository
public class UserJsonFileRepository implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserJsonFileRepository.class);

    @Value("${spring.file.datasource.path}")
    private String path;

    @Override
    public User findByUsername(final String username) {
        File dir = new File(path);
        FilenameFilter jsonFilter = (file, name) -> name.equals(username + ".json");
        File[] files = dir.listFiles(jsonFilter);
        if (files == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        User user;
        if (files.length == 0) {
            LOG.warn(String.format("There is no Json files in dir: %s ", dir));
            return null;
        } else {
                try {
                    user = objectMapper.readValue(files[0], User.class);
                } catch (IOException e) {
                    LOG.error("ERROR: '{}'", e.getMessage(), e);
                    return null;
                }
        }


        return user;
    }

    public boolean saveUser(User user) {

        File file = new File(path + user.getUsername() + "_tmp" + ".json");
        if(!file.getParentFile().mkdirs()) {
            return false;
        }

        try {
            if(!file.createNewFile()) {
                return false;
            }
        } catch (IOException e) {
            LOG.error("ERROR: '{}'", e.getMessage(), e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(file, user);
            String pathToOldFile = path + user.getUsername() + ".json";
            if (new File(pathToOldFile).exists()){
                Files.delete(Paths.get(pathToOldFile));
            }
            return file.renameTo(new File(pathToOldFile));
        } catch (JsonGenerationException e) {
            LOG.error("ERROR: '{}'", e.getMessage(), e);
            return false;
        } catch (JsonMappingException e) {
            LOG.error("ERROR: '{}'", e.getMessage(), e);
            return false;
        } catch (IOException e) {
            LOG.error("ERROR: '{}'", e.getMessage(), e);
            return false;
        }
    }
}
