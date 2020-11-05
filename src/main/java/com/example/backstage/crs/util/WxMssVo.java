package com.example.backstage.crs.util;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class WxMssVo {
    private String touser;//用户openid
    private String template_id;//订阅消息模版id
    private String page = "pages/index/index";//默认跳到小程序首页
    private List<TemplateParam> templateParamList; //推送文字

    public String toJSON() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append(String.format("\"touser\":\"%s\"", this.touser)).append(",");
        buffer.append(String.format("\"template_id\":\"%s\"", this.template_id)).append(",");
        buffer.append(String.format("\"page\":\"%s\"", this.page)).append(",");
        buffer.append("\"data\":{");
        TemplateParam param = null;
        for (int i = 0; i < this.templateParamList.size(); i++) {
            param = templateParamList.get(i);
            // 判断是否追加逗号
            if (i < this.templateParamList.size() - 1){
                buffer.append(String.format("\"%s\": {\"value\":\"%s\"},", param.getKey(), param.getValue()));
            }else{
                buffer.append(String.format("\"%s\": {\"value\":\"%s\"}", param.getKey(), param.getValue()));
            }
        }
        buffer.append("}");
        buffer.append("}");
        return buffer.toString();
    }

    public List<TemplateParam> getTemplateParamList() {
        return templateParamList;
    }

    public void setTemplateParamList(List<TemplateParam> templateParamList) {
        this.templateParamList = templateParamList;
    }
}
