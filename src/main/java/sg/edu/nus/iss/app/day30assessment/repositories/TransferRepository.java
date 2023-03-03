package sg.edu.nus.iss.app.day30assessment.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.day30assessment.models.Success;

@Repository
public class TransferRepository {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void transfer (Success success) {
        
    }

}
