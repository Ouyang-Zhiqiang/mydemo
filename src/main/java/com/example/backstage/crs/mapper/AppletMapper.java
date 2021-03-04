package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.PersonalplanEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AppletMapper {
    List<Map<Object,Object>> getUser(@Param("str") String str);
    void setPersonalplan(PersonalplanEntity personalplan);
    List<Map<Object,Object>> getPersonalplanByuserid(@Param("userid") String userid);
    void affirmPersonalplan(@Param("planid") String planid);
    List<Map<Object,Object>> getPersonalplanBycreatedby(@Param("createdby") String createdby);
    List<Map<Object,Object>> getPersonalplanAll();

}
