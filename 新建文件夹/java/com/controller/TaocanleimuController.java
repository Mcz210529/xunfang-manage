package com.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.TaocanleimuEntity;
import com.entity.view.TaocanleimuView;
import com.service.TaocanleimuService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


/**
 * 套餐类目
 * 后端接口

 */
@RestController
@RequestMapping("/taocanleimu")
public class TaocanleimuController {
    @Autowired
    private TaocanleimuService taocanleimuService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,TaocanleimuEntity taocanleimu,
		HttpServletRequest request){
        EntityWrapper<TaocanleimuEntity> ew = new EntityWrapper<TaocanleimuEntity>();
		PageUtils page = taocanleimuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, taocanleimu), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,TaocanleimuEntity taocanleimu, HttpServletRequest request){
        EntityWrapper<TaocanleimuEntity> ew = new EntityWrapper<TaocanleimuEntity>();
		PageUtils page = taocanleimuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, taocanleimu), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( TaocanleimuEntity taocanleimu){
       	EntityWrapper<TaocanleimuEntity> ew = new EntityWrapper<TaocanleimuEntity>();
      	ew.allEq(MPUtil.allEQMapPre( taocanleimu, "taocanleimu")); 
        return R.ok().put("data", taocanleimuService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(TaocanleimuEntity taocanleimu){
        EntityWrapper< TaocanleimuEntity> ew = new EntityWrapper< TaocanleimuEntity>();
 		ew.allEq(MPUtil.allEQMapPre( taocanleimu, "taocanleimu")); 
		TaocanleimuView taocanleimuView =  taocanleimuService.selectView(ew);
		return R.ok("查询套餐类目成功").put("data", taocanleimuView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TaocanleimuEntity taocanleimu = taocanleimuService.selectById(id);
        return R.ok().put("data", taocanleimu);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        TaocanleimuEntity taocanleimu = taocanleimuService.selectById(id);
        return R.ok().put("data", taocanleimu);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TaocanleimuEntity taocanleimu, HttpServletRequest request){
    	taocanleimu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(taocanleimu);
        taocanleimuService.insert(taocanleimu);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody TaocanleimuEntity taocanleimu, HttpServletRequest request){
    	taocanleimu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(taocanleimu);
        taocanleimuService.insert(taocanleimu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TaocanleimuEntity taocanleimu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(taocanleimu);
        taocanleimuService.updateById(taocanleimu);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        taocanleimuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<TaocanleimuEntity> wrapper = new EntityWrapper<TaocanleimuEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = taocanleimuService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	


}
