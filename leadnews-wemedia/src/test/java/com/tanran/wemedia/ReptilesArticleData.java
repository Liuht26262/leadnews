package com.tanran.wemedia;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/13 17:08
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.tanran.common.fastdfs.FastDfsClient;
import com.tanran.model.common.constants.WmMediaConstans;
import com.tanran.model.mappers.app.ArticleContentConfigMapper;
import com.tanran.model.mappers.app.ArticleContentMapper;
import com.tanran.model.mappers.app.ArticleMapper;
import com.tanran.model.mappers.wemedia.WmMaterialMapper;
import com.tanran.model.mappers.wemedia.WmUserMapper;
import com.tanran.model.media.dtos.WmNewsDto;
import com.tanran.model.media.pojos.WmMaterial;
import com.tanran.model.media.pojos.WmUser;
import com.tanran.utils.common.PictureGetBytesByUrlUtil;
import com.tanran.utils.threadlocal.WmThreadLocalUtils;
import com.tanran.wemedia.service.WmNewsService;

/**
 * 爬取新闻数据  用于app端演示
 * @作者 itcast
 * @创建日期 2021/4/19 21:41
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@ComponentScan("com.tanran.common.fastdfs")
public class ReptilesArticleData {
    @Autowired
    WmNewsService wmNewsService;
    @Autowired
    WmMaterialMapper wmMaterialMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleContentMapper articleContentMapper;
    @Autowired
    ArticleContentConfigMapper articleContentConfigMapper;
    @Autowired
    WmUserMapper wmUserMapper;
    @Autowired
    private FastDfsClient fastDfsClient;

    @Value("${FILE_SERVER_URL}")
    private String wenSit;

    @Test
    public void reptilesData() throws Exception {
        // 模拟当前自媒体登录用户
        WmUser wmUser = wmUserMapper.selectByPrimaryKey(12);
        WmThreadLocalUtils.setUser(wmUser);

        // 获取网易新闻数据
        String url = "https://3g.163.com/touch/news/sub/history/?ver=c&clickfrom=index2018_header_main";

        // 获取该网页document文档数据
        Document document = Jsoup.connect(url)
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36")
            .get();

        // 找到指定class的所有元素
        Elements elementsByClass = document.getElementsByClass("tab-content");
        System.out.println("div元素个数:  " + elementsByClass.size());
        Element element = elementsByClass.get(0);

        // 找到所有article标签页面元素
        Elements articleList = element.getElementsByTag("article");
        for (Element article : articleList) {
            Element aElement = article.getElementsByTag("a").get(0);
            String href = aElement.attr("href");
            System.out.println("新闻的url路径: "+href);
            // 封装WmNewsDto对象
            WmNewsDto wmNewsDto = new WmNewsDto();
            if(aElement.getElementsByClass("title").size()>0){

            }
            Element titleEle = aElement.getElementsByClass("title").get(0);
            String title = titleEle.text();
            System.out.println("新闻的文章标题:"+title);
            /*if(aElement.getElementsByClass("news-pic").size()>0){
                Element newsPic = aElement.getElementsByClass("news-pic").get(0);
                // 获取封面图片元素集合
                Elements imgList = newsPic.getElementsByTag("img");

                System.out.println("******************************************");
                System.out.println(imgList);
                System.out.println("******************************************");
                // 封面图片集合
                List<String> urlList = new ArrayList<>();
                if(urlList.size()==0){
                    wmNewsDto.setType((short)0);
                }
                for (Element imgEle : imgList) {
                    String src = imgEle.attr("data-src");
                    System.out.println("封面图片url==>" + src);

                    if (!StringUtils.isEmpty(src)) {
                        String fileName = uploadPic(src);
                        if (StringUtils.isNotBlank(fileName)) {
                            // 如果上传图片路径不为空  创建素材信息
                            WmMaterial wmMaterial = WmMaterial.builder()
                                .build();
                            wmMaterial.setUserId(WmThreadLocalUtils.getUser()
                                .getId());
                            wmMaterial.setUrl(fileName);
                            wmMaterial.setType((short) 0);
                            wmMaterial.setIsCollection((short) 0);
                            wmMaterial.setCreatedTime(new Date());
                            wmMaterialMapper.insert(wmMaterial);
                            urlList.add(fileName);
                        }
                    }
                }
                wmNewsDto.setType((short) urlList.size());
                if(urlList.size()>0){
                    wmNewsDto.setImages(urlList);
                }
            }else{
                wmNewsDto.setType((short)-1);
            }*/

            // 解析单个文章详情
            List<Map> contentMap = parseContent(href);
            wmNewsDto.setContent(JSON.toJSONString(contentMap));
            contentMap.stream().forEach(map -> {
                Collection values = map.values();
                values.forEach(s->{
                    if("http://47.243.173.194".equals(s)){
                        System.out.println("封面："+s);
                        WmMaterial wmMaterial = WmMaterial.builder()
                            .build();
                        wmMaterial.setUserId(WmThreadLocalUtils.getUser()
                            .getApAuthorId());
                        wmMaterial.setUrl((String)s);
                        wmMaterial.setType((short) 0);
                        wmMaterial.setIsCollection((short) 0);
                        wmMaterial.setCreatedTime(new Date());
                        wmMaterialMapper.insert(wmMaterial);
                    }
                    return;
                });
            });
            int i = 0;
            wmNewsDto.setTitle(title);

            try {
                Thread.sleep(300); // 睡眠1秒 让发布时间不一致
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wmNewsDto.setPublishTime(new Date());
            wmNewsDto.setStatus((short)1); // 待审核状态
            wmNewsDto.setLabels("国内");
            wmNewsDto.setChannelId(6);
            wmNewsDto.setAuthorName("星途娱乐");
            wmNewsDto.setType((short)-1);
            wmNewsService.saveNews(wmNewsDto, WmMediaConstans.WM_NEWS_SUMMIT_STATUS);
            //控制爬取的文章数据
            if(i == 50){
                return;
            }
            i++;
        }
    }




    public String uploadPic(String imgUrl) throws Exception {
        System.out.println("*****************************************************");
        System.out.println(imgUrl);
        System.out.println("*****************************************************");
        // String url = imgUrl.split("\\?")[0];
        // String uuid = UUID.randomUUID().toString().replace("-", "");
        // String suffix = url.substring(url.lastIndexOf("."));
        // InputStream in = getInputStreamByUrl("http:" + url);
        byte[] imgBytes = PictureGetBytesByUrlUtil.getImageFromURL("http:" + imgUrl);
        if(imgBytes.length!=0){
            String fileName = fastDfsClient.uploadFile(imgBytes,"jpg");
            System.out.println("上传文件名称: "+fileName);
            return fileName;
        }
        return null;
    }
    /**
     * 工具方法:  根据url路径 获取输入流
     * @param strUrl
     * @return
     */
    public static InputStream getInputStreamByUrl(String strUrl){
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(),output);
            return  new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if (conn != null) {
                    conn.disconnect();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析文章详情内容
     * @param href
     * @return
     */
    private List<Map> parseContent(String href)  {
        String url="https:"+href;
        List<Map> contentMap = new ArrayList<>();
        Document document = null;
        try {
            document = Jsoup.connect(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36")
                .get();
            Element contentDiv = document.getElementsByClass("content").get(0);
            Element pageDiv = contentDiv.getElementsByTag("div").get(0);
            Elements allElements = pageDiv.getAllElements();

            for (Element subElement : allElements) {
                String tagName = subElement.tagName();
                String className = subElement.className();
                if("p".equalsIgnoreCase(tagName)){
                    Map map = new HashMap();
                    map.put("type","text");
                    map.put("value",subElement.text());
                    contentMap.add(map);
                    System.out.println("文本内容: " + subElement.text());
                }
                if("div".equalsIgnoreCase(tagName)&&"photo".equalsIgnoreCase(className)){
                    System.out.println(subElement.getElementsByTag("img"));
                    String img = subElement.getElementsByTag("img")
                        .attr("data-src");
                    String pic = "http://47.243.173.194:1234/"+uploadPic(img);
                    System.out.println("上传图片"+pic);
                    HashMap map = new HashMap<>();
                    map.put("type","image");
                    map.put("value",pic);
                    contentMap.add(map);
                    System.out.println("图片内容: " + subElement.getElementsByTag("img").attr("data-src"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            Map map = new HashMap();
            map.put("type","text");
            map.put("value","测试文章内容");
            contentMap.add(map);
        }
        return contentMap;
    }


}