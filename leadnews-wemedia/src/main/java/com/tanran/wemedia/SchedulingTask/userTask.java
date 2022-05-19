package com.tanran.wemedia.SchedulingTask;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tanran.model.article.pojos.ApAuthor;
import com.tanran.model.mappers.app.ApUserMapper;
import com.tanran.model.mappers.app.AuthorMapper;
import com.tanran.model.mappers.wemedia.WmUserMapper;
import com.tanran.model.media.pojos.WmUser;
import com.tanran.model.user.pojos.ApUser;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 用户信息同步
 * @since 2022/5/10 13:58
 */
@Component
@Slf4j
public class userTask {
    @Autowired
    private ApUserMapper apUserMapper;
    @Autowired
    private WmUserMapper wmUserMapper;
    @Autowired
    private AuthorMapper authorMapper;

    private  ApUser apUser = null;
    private  WmUser wmUser = null;

    /**
     * 先查询用户表中flag=1的最新数据以及管理员用户表中的最新数据
     * 如果数据一致就不操作，如果数据不一致就进行同步
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void synchronizeArticle(){
        WmUser wm = wmUserMapper.selectWmUser("1");
        if(Objects.isNull(wmUser)||!wm.getId().equals(wmUser.getId())){
            wmUser = wm;
        }
        log.info("=====================开始同步用户信息=================");
        ApUser user = apUserMapper.selectApWmUser((short)1);
        if(Objects.isNull(apUser)||!user.getId().equals(apUser.getId())){
            apUser = user;
        }
        System.out.println("数据库中的最新apUser"+user);
        System.out.println("数据库的最新wmUser"+wm);
        if(Objects.nonNull(wmUser)&&Objects.nonNull(apUser)){
            /**进行数据的比较,如果数据不一致就进行同步*/
            if(!apUser.getId().equals(wmUser.getApUserId().longValue())){
                System.out.println("user"+apUser);
                System.out.println("wm"+wmUser);
                WmUser newWmUser = new WmUser();
                newWmUser.setPassword(apUser.getPassword());
                newWmUser.setPhone(apUser.getPhone());
                newWmUser.setSalt(apUser.getSalt());
                newWmUser.setImage(apUser.getImage());
                newWmUser.setRoleType("1");
                newWmUser.setStatus((byte)1);
                newWmUser.setCreatedTime(apUser.getCreatedTime());
                newWmUser.setApUserId(apUser.getId().intValue());
                newWmUser.setEmail(apUser.getMail());
                newWmUser.setName(apUser.getName());
                newWmUser.setNickname(apUser.getName());
                /**创建对应的作者信息*/
                ApAuthor apAuthor = new ApAuthor();
                apAuthor.setCreatedTime(apUser.getCreatedTime());
                apAuthor.setName(apUser.getName());
                apAuthor.setPhoto(apAuthor.getPhoto());
                apAuthor.setType((short)1);
                authorMapper.insertSelective(apAuthor);
                ApAuthor apAuthor1 = authorMapper.selectAuthorSelective(apAuthor);
                if(Objects.nonNull(apAuthor1)){
                    newWmUser.setApAuthorId(apAuthor1.getId());
                }
                wmUserMapper.insertSelective(newWmUser);
                log.info("=====================已更新[]条数据=================",1);
            }
        }
    }
}
