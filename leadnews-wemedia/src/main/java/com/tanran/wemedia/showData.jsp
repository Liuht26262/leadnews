<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>显示100以内的质数</title>
</head>
<body>
<%!
    //声明变量
    private int i,j,count = 1;
    private List<Integer> list;

%>
<%!

%>
<%
    System.out.println("100以内的质数为：<br>2");
    for(i=3;i<100;i=i+2){
        for(j=2;j<i;j++){
            if(i%j==0){
                break;
            }
            if(j==i){
                list.add(i);
                count++;
                System.out.println(i+"");
                if(count%10 == 0){
                    System.out.println("<br>");
                }
            }
        }
    }

%>
<h1>结果如下：</h1>
<p><% list.stream().forEach(s -> System.out.println(s)); %></p>
</body>
</body>
</html>
