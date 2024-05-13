package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.ShangchuantijianbaogaoEntity;
import com.entity.view.ShangchuantijianbaogaoView;
import com.entity.vo.ShangchuantijianbaogaoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 上传体检报告

 */
public interface ShangchuantijianbaogaoDao extends BaseMapper<ShangchuantijianbaogaoEntity> {
	
	List<ShangchuantijianbaogaoVO> selectListVO(@Param("ew") Wrapper<ShangchuantijianbaogaoEntity> wrapper);
	
	ShangchuantijianbaogaoVO selectVO(@Param("ew") Wrapper<ShangchuantijianbaogaoEntity> wrapper);
	
	List<ShangchuantijianbaogaoView> selectListView(@Param("ew") Wrapper<ShangchuantijianbaogaoEntity> wrapper);

	List<ShangchuantijianbaogaoView> selectListView(Pagination page,@Param("ew") Wrapper<ShangchuantijianbaogaoEntity> wrapper);
	
	ShangchuantijianbaogaoView selectView(@Param("ew") Wrapper<ShangchuantijianbaogaoEntity> wrapper);
	
}
