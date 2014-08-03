package com.test.batch.simple.processor;

import com.test.batch.simple.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.UUID;

/**
 * Created by Armando on 28/07/2014.
 */
public class LogUserProcessor<T> implements ItemProcessor<User, User> {

    private static final Log log = LogFactory.getLog(LogUserProcessor.class);

    public User process(User user) throws Exception {
        System.out.println(" -----------------------  about to PROCESS data item -----------------------");

        //Log user before processing
        log.info(user.toString());

        //Do some dummy processing on the user obj

        //Modify user id, add UUID at the end
        String newUserId = user.getId() + "_proc_" + UUID.randomUUID();

        //Set new id including UUID
        user.setId(newUserId);

        if (user.getEmail() != null) {
            String mail = user.getEmail().replace("mail", "corp-mail").toLowerCase();
            user.setEmail(mail);
        }

        //Once the item data has been processed return it
        return user;
    }
}
