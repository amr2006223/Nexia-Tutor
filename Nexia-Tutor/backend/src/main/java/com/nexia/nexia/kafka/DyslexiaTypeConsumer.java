package com.nexia.nexia.kafka;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.basedomain.basedomain.dto.DyslexiaTypeEvent;
import com.example.basedomain.basedomain.dto.DyslexiaTypesDTO;
import com.nexia.nexia.models.DyslexiaType;
import com.nexia.nexia.models.User;
import com.nexia.nexia.repositories.DyslexiaTypeRepository;
import com.nexia.nexia.repositories.UserRepository;

@Service
public class DyslexiaTypeConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DyslexiaTypeConsumer.class);
    @Autowired
    DyslexiaTypeRepository dyslexiaTypeRepository;
    @Autowired
    UserRepository userRepository;

    @KafkaListener(topics = "DYSLEXIATYPE" ,groupId = "${spring.kafka.consumer.group-id}")
     public void Consume(DyslexiaTypeEvent dyslexiaTypeEvent){
        LOGGER.info(String.format("User event recieved => %s" , dyslexiaTypeEvent.toString()));
        //save user in database
        List<DyslexiaTypesDTO> userCategories = dyslexiaTypeEvent.getDylsexiaTypes();
        User user = userRepository.findById(dyslexiaTypeEvent.getUserId()).orElse(null);
        for (DyslexiaTypesDTO dyslexiaType : userCategories) {
            DyslexiaType newDyslexiaType = new DyslexiaType(dyslexiaType.getId(),dyslexiaType.getName());
            try{
                if(user == null) break;
                if(!dyslexiaTypeRepository.findById(dyslexiaType.getId()).isPresent()) {
                    dyslexiaTypeRepository.save(newDyslexiaType);
                    user.getDyslexia_types().add(newDyslexiaType);
                    userRepository.save(user);
                    continue;
                }
                if(!user.getDyslexia_types().contains(newDyslexiaType)){
                    user.getDyslexia_types().add(newDyslexiaType);
                }

                
            }catch(Exception e){
                return;
            }
        }
     }
    }