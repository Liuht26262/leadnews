package com.tanran.model.mappers.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.user.pojos.ApUserSearch;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/3/31 9:43
 */
public interface UserSearchMapper {
    List<ApUserSearch> selectUserSearch(@Param("entryId") Integer entryId, @Param("pageSize") int pageSize);

    Integer deleteUserSearch(@Param("entryId") Integer entryId, @Param("idList") List<Integer> idList);

    Integer clearUserSearch(@Param("EntryId")Integer EntryId);

    int saveUserSearch(ApUserSearch userSearch);

    int checkSearch(@Param("entryId")Integer entryId,@Param("keyWords")String keyWords);

}
