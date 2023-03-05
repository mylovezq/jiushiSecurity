package vo;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * 分页数据封装类
 */
@Data
public class CommonPage<T> {
    private Long pageNum;
    private Long pageSize;
    private Long totalPage;
    private Long total;
    private List<T> list;

    public static <T>CommonPage<T> restPage(IPage<T> list){
        CommonPage<T> result = new CommonPage<>();
        result.setList(list.getRecords());
        result.setTotal(list.getTotal());
        result.setPageSize( list.getSize());
        result.setTotalPage( list.getPages());
        result.setPageNum(list.getCurrent());
        return result;
    }
}
