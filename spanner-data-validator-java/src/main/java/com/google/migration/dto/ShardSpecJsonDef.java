/*
 Copyright 2024 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.google.migration.dto;

import com.google.migration.Helpers;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShardSpecJsonDef {
  private static final Logger LOG = LoggerFactory.getLogger(ShardSpecJsonDef.class);

  private String hostnamePrefix;
  private String dbNamePrefix;
  private Integer hostCount;
  private Integer hostnameSuffixStart;
  private Integer hostnameSuffixDigits;
  private Integer shardCount;
  private Integer shardSuffixStart;
  private Integer shardSuffixDigits;
  private String shardStaticSuffix;
  private String username;
  private String password;

  private ShardSpecJsonDef() {
  }

  public static ShardSpecJsonDef fromJsonResourceFile(String resourceName) {
    try {
      ClassLoader classloader = Thread.currentThread().getContextClassLoader();
      InputStream is = classloader.getResourceAsStream(resourceName);

      String jsonStr = new String(is.readAllBytes(), StandardCharsets.UTF_8);
      return fromJsonString(jsonStr);
    } catch (Exception ex) {
      LOG.error("Exception while loading shard spec from json file");
      LOG.error(ex.getMessage());
      LOG.error(ex.getStackTrace().toString());
    }

    return null;
  }

  public static ShardSpecJsonDef fromJsonFile(String projectId, String jsonFile) {
    String jsonStr = null;

    GCSObject gcsObject = Helpers.getGCSObjectFromFullPath(jsonFile);
    if(gcsObject != null) {
      jsonStr = Helpers.getFileFromGCS(projectId, gcsObject.getBucket(), gcsObject.getObjectName());
    }

    try {
      if(Helpers.isNullOrEmpty(jsonStr)) {
        jsonStr = FileUtils.readFileToString(new File(jsonFile), StandardCharsets.UTF_8);
      }
      return fromJsonString(jsonStr);
    } catch (Exception ex) {
      LOG.error("Exception while loading shard spec from json file");
      LOG.error(ex.getMessage());
      LOG.error(ex.getStackTrace().toString());
    }

    return null;
  }

  public static ShardSpecJsonDef fromJsonString(String jsonStr) {
    try {
      JSONObject jsonObject = new JSONObject(jsonStr);

      ShardSpecJsonDef ssJsonDef = new ShardSpecJsonDef();

      ssJsonDef.setHostnamePrefix(jsonObject.getString("hostname-prefix"));
      ssJsonDef.setDbNamePrefix(jsonObject.getString("dbname-prefix"));
      ssJsonDef.setHostCount(jsonObject.getInt("host-count"));
      ssJsonDef.setHostnameSuffixStart(jsonObject.getInt("hostname-suffix-start"));
      ssJsonDef.setHostnameSuffixDigits(jsonObject.getInt("hostname-suffix-digits"));
      ssJsonDef.setShardCount(jsonObject.getInt("shard-count"));
      ssJsonDef.setShardSuffixStart(jsonObject.getInt("shard-suffix-start"));
      ssJsonDef.setShardSuffixDigits(jsonObject.getInt("shard-suffix-digits"));
      ssJsonDef.setUsername(jsonObject.getString("username"));
      ssJsonDef.setPassword(jsonObject.getString("password"));

      if(!jsonObject.isNull("shard-static-suffix")) {
        ssJsonDef.setShardStaticSuffix(jsonObject.getString("shard-static-suffix"));
      }

      return ssJsonDef;
    } catch (Exception ex) {
      LOG.error("Exception while loading shard spec from json file");
      LOG.error(ex.getMessage());
      LOG.error(ex.getStackTrace().toString());
    }

    return null;
  }

  public String getHostnamePrefix() {
    return hostnamePrefix;
  }

  public void setHostnamePrefix(String hostnamePrefix) {
    this.hostnamePrefix = hostnamePrefix;
  }

  public String getDbNamePrefix() {
    return dbNamePrefix;
  }

  public void setDbNamePrefix(String dbNamePrefix) {
    this.dbNamePrefix = dbNamePrefix;
  }

  public Integer getHostCount() {
    return hostCount;
  }

  public void setHostCount(Integer hostCount) {
    this.hostCount = hostCount;
  }

  public Integer getHostnameSuffixStart() {
    return hostnameSuffixStart;
  }

  public void setHostnameSuffixStart(Integer hostnameSuffixStart) {
    this.hostnameSuffixStart = hostnameSuffixStart;
  }

  public Integer getHostnameSuffixDigits() {
    return hostnameSuffixDigits;
  }

  public void setHostnameSuffixDigits(Integer hostnameSuffixDigits) {
    this.hostnameSuffixDigits = hostnameSuffixDigits;
  }

  public Integer getShardCount() {
    return shardCount;
  }

  public void setShardCount(Integer shardCount) {
    this.shardCount = shardCount;
  }

  public Integer getShardSuffixStart() {
    return shardSuffixStart;
  }

  public void setShardSuffixStart(Integer shardSuffixStart) {
    this.shardSuffixStart = shardSuffixStart;
  }

  public Integer getShardSuffixDigits() {
    return shardSuffixDigits;
  }

  public void setShardSuffixDigits(Integer shardSuffixDigits) {
    this.shardSuffixDigits = shardSuffixDigits;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getShardStaticSuffix() {
    return shardStaticSuffix;
  }

  public void setShardStaticSuffix(String shardStaticSuffix) {
    this.shardStaticSuffix = shardStaticSuffix;
  }
} // class ShardSpecJsonDef