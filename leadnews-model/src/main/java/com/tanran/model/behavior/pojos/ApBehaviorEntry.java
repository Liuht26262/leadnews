package com.tanran.model.behavior.pojos;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;

@Data
public class ApBehaviorEntry {
    private Integer id;
    private Short type;
    private Integer entryId;
    private Date createdTime;

    @Alias("ApBehaviorEntryEnumType")
    public enum  Type{
        USER((short)1),EQUIPMENT((short)0);
        @Getter
        short code;
        Type(short code){
            this.code = code;
        }
    }

    public boolean isUser(){
        if(this.getType()!=null&&this.getType()== Type.USER.getCode()){
            return true;
        }
        return false;
    }
}