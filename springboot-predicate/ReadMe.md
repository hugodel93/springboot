# 说明
使用指定语法，判断Map对象是否符合条件。

# 语法
## 关键字
### 逻辑
- and 多条件与关联，短路判断
- or 多条件或关联，短路判断
- not 多条件非关联，短路判断
- undefined 多条件无关联，用户自定义判断方式
### 判断
- eq 等于 `['eq','age',20]`
- ne 不等于 `['ne','age',20]`
- lt 小于 `['lt','age',20]`
- le 小于等于 `['le','age',20]`
- gt 大于 `['gt','age',20]`
- ge 大于等于 `['ge','age',20]`
- re_match 正则 `['re_match', 'message', ['quote', '.*broker.*']]`
- exists 是否存在 `['exists', 'timestamp']`
- true 真 `['true', 'enabled']`
- false 假 `['false', 'enabled']`
- in 存在于 `['in', 'skills', ['Java', 'Spring', 'AWS']]`
- between 范围值（含边界）`['between', 'age', 10 ,29]`
### 引用
- quote 字面量字符串，或转义标记。 为了表示一个字符串应该被当作字面量处理，而不是变量，该字符串必须使用 quote 表达式进行引用。
  ```["eq", "employee", ["quote", "Tom"]] 表示employee的值是否为“Tom”```
  ```["eq", "employee", "Tom"] 表示employee的值是否和Tom的“值”一样```
## 示例
```java
String predicate = "['and'," +
                "['re_match','name',['quote','[a-zA-Z]?ross']]," +
                "['and',['exists','age']," +
                "['or',['gt','age',27],['lt','age',12]]" +
                "]" +
                "]";
String predocate = "['or'," +
        "['eq','name',['quote','tross']]," +
        "['and',['eq','name',['quote','jross']]," +
        "['eq','address',['quote','China']]," +
        "['eq',['quote','Wuhan'],'town']" +
        "]" +
        "]";
```
# 接口
## 单个判断
```java
Map<String, Object> queryMap = new HashMap<>();
// put...
String predicate = "['ne','phone','phone2']";
Expression expression = Expression.createExpression(predicate);
boolean rest = expression.evaluate(queryMap);

```
## 自定义判断
```java
Map<String, Object> queryMap = new HashMap<>();
// put...
String predicate = "['undefined'," +
        "['eq','packageName',['quote','org.example']]," +
        "['eq','className',['quote','test']]," +
        "['ge', 'version', ['quote', '1.0.0']], " +
        "['ne', 'status', ['quote', 'inactive']], " +
        "['false', 'enabled'], " +
        "['re_match', 'message', ['quote', '.*broker.*']]," +
        "['gt', 'age', 20]," +
        "['exists', 'timestamp']," +
        "['in', 'skills', ['Java', 'Spring', 'AWS']]," +
        "]";
UndefinedExpression expression = (UndefinedExpression) Expression.createExpression(predicate);
for(Expression exp : expression.getAllExpressions()){
    System.out.printf("exp: %s %n", exp.evaluate(queryMap));
}
```
## 逻辑判断
```java
String predocate = "['or'," +
                "['eq','name',['quote','tross']]," +
                "['and',['eq','name',['quote','jross']]," +
                "['eq','address',['quote','China']]," +
                "['eq',['quote','Wuhan'],'town']" +
                "]" +
                "]";
Expression expression = Expression.createExpression(predocate);
boolean rest = expression.evaluate(queryMap);
Assertions.assertTrue(rest);
```
## 自定义表达式
```java
/**
 * list[0]: not/and/or
 * list[n]: list["{op}","{key}","{value}"] 或 list["{logic}",list]
 **/
// org.example.Expression.createExpression(java.util.List)

```