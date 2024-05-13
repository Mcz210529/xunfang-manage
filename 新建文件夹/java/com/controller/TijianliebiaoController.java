package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.TijianliebiaoEntity;
import com.entity.view.TijianliebiaoView;
import com.service.TijianliebiaoService;
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
 * 体检列表
 * 后端接口

 */
@RestController
@RequestMapping("/tijianliebiao")
public class TijianliebiaoController {
    @Autowired
    private TijianliebiaoService tijianliebiaoService;


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, TijianliebiaoEntity tijianliebiao,
                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date yuyuekaishishijianstart,
                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date yuyuekaishishijianend,
                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date yuyuejieshushijianstart,
                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date yuyuejieshushijianend,
                  HttpServletRequest request) {
        EntityWrapper<TijianliebiaoEntity> ew = new EntityWrapper<TijianliebiaoEntity>();
        if (yuyuekaishishijianstart != null) ew.ge("yuyuekaishishijian", yuyuekaishishijianstart);
        if (yuyuekaishishijianend != null) ew.le("yuyuekaishishijian", yuyuekaishishijianend);
        if (yuyuejieshushijianstart != null) ew.ge("yuyuejieshushijian", yuyuejieshushijianstart);
        if (yuyuejieshushijianend != null) ew.le("yuyuejieshushijian", yuyuejieshushijianend);
        PageUtils page = tijianliebiaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tijianliebiao), params), params));

        return R.ok().put("data", page);
    }

    /**
     * 前端列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params, TijianliebiaoEntity tijianliebiao) {
        EntityWrapper<TijianliebiaoEntity> ew = new EntityWrapper<>();
        PageUtils page = tijianliebiaoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, tijianliebiao), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(TijianliebiaoEntity tijianliebiao) {
        EntityWrapper<TijianliebiaoEntity> ew = new EntityWrapper<TijianliebiaoEntity>();
        ew.allEq(MPUtil.allEQMapPre(tijianliebiao, "tijianliebiao"));
        return R.ok().put("data", tijianliebiaoService.selectListView(ew));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(TijianliebiaoEntity tijianliebiao) {
        EntityWrapper<TijianliebiaoEntity> ew = new EntityWrapper<TijianliebiaoEntity>();
        ew.allEq(MPUtil.allEQMapPre(tijianliebiao, "tijianliebiao"));
        TijianliebiaoView tijianliebiaoView = tijianliebiaoService.selectView(ew);
        return R.ok("查询体检列表成功").put("data", tijianliebiaoView);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        TijianliebiaoEntity tijianliebiao = tijianliebiaoService.selectById(id);
        return R.ok().put("data", tijianliebiao);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        TijianliebiaoEntity tijianliebiao = tijianliebiaoService.selectById(id);
        return R.ok().put("data", tijianliebiao);
    }


    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TijianliebiaoEntity tijianliebiao, HttpServletRequest request) {
        tijianliebiao.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        //ValidatorUtils.validateEntity(tijianliebiao);
        tijianliebiaoService.insert(tijianliebiao);
        return R.ok();
    }

    /**
     * 前端保存
     */
    @IgnoreAuth
    @RequestMapping("/add")
    public R add(@RequestBody TijianliebiaoEntity tijianliebiao, HttpServletRequest request) {
        tijianliebiao.setId(new Date().getTime() + new Double(Math.floor(Math.random() * 1000)).longValue());
        //ValidatorUtils.validateEntity(tijianliebiao);
        tijianliebiaoService.insert(tijianliebiao);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TijianliebiaoEntity tijianliebiao, HttpServletRequest request) {
        //ValidatorUtils.validateEntity(tijianliebiao);
        tijianliebiaoService.updateById(tijianliebiao);//全部更新
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        tijianliebiaoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 提醒接口
     */
    @RequestMapping("/remind/{columnName}/{type}")
    public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request,
                         @PathVariable("type") String type, @RequestParam Map<String, Object> map) {
        map.put("column", columnName);
        map.put("type", type);

        if (type.equals("2")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            Date remindStartDate = null;
            Date remindEndDate = null;
            if (map.get("remindstart") != null) {
                Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
                c.setTime(new Date());
                c.add(Calendar.DAY_OF_MONTH, remindStart);
                remindStartDate = c.getTime();
                map.put("remindstart", sdf.format(remindStartDate));
            }
            if (map.get("remindend") != null) {
                Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
                c.setTime(new Date());
                c.add(Calendar.DAY_OF_MONTH, remindEnd);
                remindEndDate = c.getTime();
                map.put("remindend", sdf.format(remindEndDate));
            }
        }

        Wrapper<TijianliebiaoEntity> wrapper = new EntityWrapper<TijianliebiaoEntity>();
        if (map.get("remindstart") != null) {
            wrapper.ge(columnName, map.get("remindstart"));
        }
        if (map.get("remindend") != null) {
            wrapper.le(columnName, map.get("remindend"));
        }


        int count = tijianliebiaoService.selectCount(wrapper);
        return R.ok().put("count", count);
    }


}
