package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.TaocanyuyueEntity;
import com.entity.view.TaocanyuyueView;
import com.service.TaocanyuyueService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


/**
 * 套餐预约
 * 后端接口
 */
@RestController
@RequestMapping("/taocanyuyue")
public class TaocanyuyueController {
    @Autowired
    private TaocanyuyueService taocanyuyueService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,TaocanyuyueEntity taocanyuyue,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date yuyueriqistart, 
    		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date yuyueriqiend,
		HttpServletRequest request){
    	if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
    		taocanyuyue.setUserid((Long)request.getSession().getAttribute("userId"));
    	}
        EntityWrapper<TaocanyuyueEntity> ew = new EntityWrapper<TaocanyuyueEntity>();
		if(yuyueriqistart!=null) ew.ge("yuyueriqi", yuyueriqistart);
        	if(yuyueriqiend!=null) ew.le("yuyueriqi", yuyueriqiend);
		PageUtils page = taocanyuyueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, taocanyuyue), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,TaocanyuyueEntity taocanyuyue, HttpServletRequest request){
        EntityWrapper<TaocanyuyueEntity> ew = new EntityWrapper<TaocanyuyueEntity>();
		PageUtils page = taocanyuyueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, taocanyuyue), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( TaocanyuyueEntity taocanyuyue){
       	EntityWrapper<TaocanyuyueEntity> ew = new EntityWrapper<TaocanyuyueEntity>();
      	ew.allEq(MPUtil.allEQMapPre( taocanyuyue, "taocanyuyue")); 
        return R.ok().put("data", taocanyuyueService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(TaocanyuyueEntity taocanyuyue){
        EntityWrapper< TaocanyuyueEntity> ew = new EntityWrapper< TaocanyuyueEntity>();
 		ew.allEq(MPUtil.allEQMapPre( taocanyuyue, "taocanyuyue")); 
		TaocanyuyueView taocanyuyueView =  taocanyuyueService.selectView(ew);
		return R.ok("查询套餐预约成功").put("data", taocanyuyueView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TaocanyuyueEntity taocanyuyue = taocanyuyueService.selectById(id);
        return R.ok().put("data", taocanyuyue);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        TaocanyuyueEntity taocanyuyue = taocanyuyueService.selectById(id);
        return R.ok().put("data", taocanyuyue);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TaocanyuyueEntity taocanyuyue, HttpServletRequest request){
    	taocanyuyue.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(taocanyuyue);
    	taocanyuyue.setUserid((Long)request.getSession().getAttribute("userId"));
        taocanyuyueService.insert(taocanyuyue);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody TaocanyuyueEntity taocanyuyue, HttpServletRequest request){
    	taocanyuyue.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(taocanyuyue);
        taocanyuyueService.insert(taocanyuyue);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TaocanyuyueEntity taocanyuyue, HttpServletRequest request){
        //ValidatorUtils.validateEntity(taocanyuyue);
        taocanyuyueService.updateById(taocanyuyue);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        taocanyuyueService.deleteBatchIds(Arrays.asList(ids));
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
		
		Wrapper<TaocanyuyueEntity> wrapper = new EntityWrapper<TaocanyuyueEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}
		if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
    		wrapper.eq("userid", (Long)request.getSession().getAttribute("userId"));
    	}


		int count = taocanyuyueService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	


}
