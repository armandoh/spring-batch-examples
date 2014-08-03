package com.test.batch.partition.processor;

import com.test.batch.partition.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by Armando on 28/07/2014.
 */
public class SecondUserProcessor<T> implements ItemProcessor<User, User> {

    private static final Log log = LogFactory.getLog(SecondUserProcessor.class);

    public User process(User user) throws Exception {
        System.out.println(" #######################  about to PROCESS data item #######################");

        //Log user before processing
        log.info(user.toString());

        //Do some dummy processing on the user obj

        //Modify user id, add UUID at the end
        String newUserId = user.getId() + "_sec_proc_" + java.util.UUID.randomUUID();

        //Set new id including UUID
        user.setId(newUserId);

        if (user.getEmail() != null) {
            String mail = user.getEmail().replace("mail", "new-corp-mail").toUpperCase();
            user.setEmail(mail);
        }

        //Once the item data has been processed return it
        return user;
    }
}
