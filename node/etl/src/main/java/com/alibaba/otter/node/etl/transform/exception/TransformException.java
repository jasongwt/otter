/*
 * Copyright (C) 2010-2101 Alibaba Group Holding Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.otter.node.etl.transform.exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author jianghang 2011-9-16 下午01:59:25
 * @version 4.0.0
 */
public class TransformException extends NestableRuntimeException {

    private static final long serialVersionUID = -7288830284122672209L;

    private String            errorCode;
    private String            errorDesc;

    public TransformException(String errorCode){
        super(errorCode);
    }

    public TransformException(String errorCode, Throwable cause){
        super(errorCode, cause);
    }

    public TransformException(String errorCode, String errorDesc){
        super(errorCode + ":" + errorDesc);
    }

    public TransformException(String errorCode, String errorDesc, Throwable cause){
        super(errorCode + ":" + errorDesc, cause);
    }

    public TransformException(Throwable cause){
        super(cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}