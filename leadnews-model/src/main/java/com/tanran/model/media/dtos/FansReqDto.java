package com.tanran.model.media.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/8 18:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FansReqDto{
    private Integer id;
    private Integer userId;
    protected Integer size;
    protected Integer page;


    public void checkParam() {
        if (this.page == null || this.page < 0) {
            setPage(1);
        }
        if (this.size == null || this.size < 0 || this.size > 100) {
            setSize(10);
        }
    }
}
