/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fu.com.parttimejob.bean;

import java.io.Serializable;

/**
 * ================================================
 
 * ================================================
 */
public class ResponseBean<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public String msg;
    public T data;
    public String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LzyResponse{\n" +//
               "\tmsg='" + msg + "\'\n" +//
               "\tdata=" + data + "\n" +//
               '}';
    }
}
