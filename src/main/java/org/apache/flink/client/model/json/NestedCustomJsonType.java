package org.apache.flink.client.model.json;

/**
 * Created by Dmitry on 28.03.2018.
 */
public class NestedCustomJsonType {

    private String f21;

    public String getF21() {
        return f21;
    }

    public void setF21(String f21) {
        this.f21 = f21;
    }

    @Override
    public String toString() {
        return "NestedCustomJsonType{" +
                "f21='" + f21 + '\'' +
                '}';
    }
}
