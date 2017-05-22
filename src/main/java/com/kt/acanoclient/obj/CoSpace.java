package com.kt.acanoclient.obj;

import com.kt.acanoclient.anno.AcanoType;
import com.kt.acanoclient.anno.ID;
import org.dom4j.Node;

/**
 * Created by Vega Zhou on 2017/5/19.
 */
@AcanoType("coSpace")
public class CoSpace extends AcanoObject {
    @ID
    private String id;

    private String name;
    private String uri;
    private String callId;
    private String defaultLayout;

    public String getId() {
        return id;
    }

    @Override
    public void parseBody(Node bodyNode) {
        id = readTextValue(bodyNode.selectSingleNode("@id"));
        name = readTextValue(bodyNode.selectSingleNode("name"));
        uri = readTextValue(bodyNode.selectSingleNode("uri"));
        callId = readTextValue(bodyNode.selectSingleNode("callId"));
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getDefaultLayout() {
        return defaultLayout;
    }

    public void setDefaultLayout(String defaultLayout) {
        this.defaultLayout = defaultLayout;
    }


    @Override
    public String getNewObjectPath() {
        return "/coSpaces";
    }
}
