# Spting boot项目运行 #
**方式1 可以直接软件里面运行**

**方式2 ：可以使用命令在当前的项目下运行 ： 用命令启动,到项目目录下面。运行:mvn spring-boot:run**

**方式3:mvn install,然后可以在项目下面的"target"目录下面看到项目de jar文件
java -jar girl-0.0.0.1-snapshot.jar**


# 项目属性配置 #
**Application.java
每次可以通过运行该类，启动服务。**

**配置文件application.properties和application.yml任意一个就行**

## application.properties中配置 ##
**server.port=8081    端口**

**server.context-path=/girl   端口路径**

## application.yml中配置  ##   
**这个文件是自己新建的，名字一定要这样命名才有效**

    server:
      port: 8081 端口
      context-path: /girl   //访问路径
项目访问路径是:http://localhost:8080/girl/hello


# Controller的使用 #
**@Controller:处理http请求**

**@RestController:Spring4之后新加的注解，原来返回json需要**

**@ResponseBody配合@Controller**

**@RequestMapping:配置url映射**

## 1. @Controller类使用举例 ##
    pom.xml增加
    
    <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    
    访问类中配置
    @RequestMapping(value={"/hello","/hi"},method=RequestMethod.GET)
    映射了两个地址
    @PathVariable:获取url中的数据
    @RequestParam:获取请求参数的值
    @GetMapping:组合注解
    
    Controller的写法
    @RequestMapping(value="/hello/{id}",method=RequestMethod.GET)
    public String say(@PathVariable("id") Integer id){
    	return "id:"+id;
    }
    访问路径：http://localhost:8081/hello/44


## 情况二 Controller的写法 ##

    @RequestMapping(value="/hello",method=RequestMethod.GET)
    public String say(@RequestParam("id") Integer id){
    	return "id:"+id;
    }
    访问路径:http://localhost:8081/hello?id=2
    id为空会报错。那么下面设置可以避免  设置默认值
    
    /*@RequestMapping(value="/hello",method=RequestMethod.GET)*/
    /*简化RequestMethod.GET方式。同理还有@PostMapping*/
    @GetMapping(value="/hello")
    public String say(@RequestParam(value="id",required=false,defaultValue = "0") Integer id){ 
    	return "id:"+id;
    }

# Mybatis配置 #
## 导入对应的包 ##
    mybatis的包
    <!-- https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter -->
    <dependency>
    	<groupId>org.mybatis.spring.boot</groupId>
    	<artifactId>mybatis-spring-boot-starter</artifactId>
    	<version>1.3.1</version>
    </dependency>


    mybatis的jdbc的整合包
    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
    <dependency>
    	<groupId>mysql</groupId>
    	<artifactId>mysql-connector-java</artifactId>
    	<version>8.0.11</version>
    </dependency>


    在idea中要再导入
    <!-- https://mvnrepository.com/artifact/junit/junit -->
    <dependency>
    	<groupId>junit</groupId>
    	<artifactId>junit</artifactId>
    	<version>4.12</version>
    </dependency>

## 1 然后再application.properties中配置连接数据库的数据和账号密码 ##
    mybatis.type-aliases-package=com.neo.entity
    spring.datasource.driverClassName = com.mysql.cj.jdbc.Driver
    spring.datasource.url = jdbc:mysql://127.0.0.1:3307/lxdome?characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC
    spring.datasource.username = root
    spring.datasource.password = 123456
    
    springboot会自动加载spring.datasource.*相关配置，数据源就会自动注入到sqlSessionFactory中，sqlSessionFactory会自动注入到Mapper中，对了你一切都不用管了，直接拿起来使用就行了。

## 2 在启动类中添加对mapper包扫描@MapperScan ##
    @SpringBootApplication
    @MapperScan("com.neo.mapper")
    public class Application {
    
    	public static void main(String[] args) {
   			SpringApplication.run(Application.class, args);
    	}
	}
    或者直接在Mapper类上面添加注解@Mapper,建议使用上面那种直接扫描包，不然每个mapper加个注解也挺麻烦的

## 3第一种配置方式 注解类配置  开发Mapper注解的方式   ##
**定义使用的链接xml的接口类**   注意数据的实体类创建
> 第三步是最关键的一块，sql生产都在这里
    public interface UserMapper {

    @Select("SELECT * FROM users")
    @Results({              对应参数注解
    	@Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
    	@Result(property = "nickName", column = "nick_name")
    })
    List<UserEntity> getAll();
    
    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
    	@Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
    	@Result(property = "nickName", column = "nick_name")
    })
    UserEntity getOne(Long id);
    
    @Insert("INSERT INTO users(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
    void insert(UserEntity user);
    
    @Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
    void update(UserEntity user);
    
    @Delete("DELETE FROM users WHERE id =#{id}")
    void delete(Long id);
    }
    
    @Select 是查询类的注解，所有的查询均使用这个
    @Result 修饰返回的结果集，关联实体类属性和数据库字段一一对应，如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。
    @Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
    @Update 负责修改，也可以直接传入对象
    @delete 负责删除


## 访问层使用 ##

**上面三步就基本完成了相关dao层开发，使用的时候当作普通的类注入进入就可以了**
    @RunWith(SpringRunner.class)
    @SpringBootTest
    public class UserMapperTest {
    
    	@Autowired  直接注入
    	private UserMapper UserMapper;
    
    	@Test
    	public void testInsert() throws Exception {
    		UserMapper.insert(new UserEntity("aa", "a123456", UserSexEnum.MAN));
    		UserMapper.insert(new UserEntity("bb", "b123456", UserSexEnum.WOMAN));
    		UserMapper.insert(new UserEntity("cc", "b123456", UserSexEnum.WOMAN));
    
    		Assert.assertEquals(3, UserMapper.getAll().size());
    	}
    
    	@Test
    	public void testQuery() throws Exception {
    		List<UserEntity> users = UserMapper.getAll();
    		System.out.println(users.toString());
    	}
    
    	@Test
    	public void testUpdate() throws Exception {
    		UserEntity user = UserMapper.getOne(3l);
    		System.out.println(user.toString());
    		user.setNickName("neo");
    		UserMapper.update(user);
    		Assert.assertTrue(("neo".equals(UserMapper.getOne(3l).getNickName())));
    	}}
    }




# 使用xml 的方式 #
**极简xml版本**

**极简xml版本保持映射文件的老传统，优化主要体现在不需要实现dao的是实现层，系统会自动根据方法名在映射文件中找对应的sql.**
1、配置
**pom文件和上个版本一样，只是application.properties新增以下配置**
**mybatis.config-locations=classpath:mybatis/mybatis-config.xml   xml文件的位置l**
**mybatis.mapper-locations=classpath:mybatis/mapper/*.xml     多个xml文件的位置**
**指定了mybatis基础配置文件和实体类映射文件的地址**
    mybatis-config.xml 配置
    <configuration>
	    <typeAliases>
		    <typeAlias alias="Integer" type="java.lang.Integer" />
		    <typeAlias alias="Long" type="java.lang.Long" />
		    <typeAlias alias="HashMap" type="java.util.HashMap" />
		    <typeAlias alias="LinkedHashMap" type="java.util.LinkedHashMap" />
		    <typeAlias alias="ArrayList" type="java.util.ArrayList" />
		    <typeAlias alias="LinkedList" type="java.util.LinkedList" />
	    </typeAliases>
	</configuration>
    这里也可以添加一些mybatis基础的配置
    2、添加User的映射文件
    <mapper namespace="com.neo.mapper.UserMapper" >
	    <resultMap id="BaseResultMap" type="com.neo.entity.UserEntity" >
		    <id column="id" property="id" jdbcType="BIGINT" />
		    <result column="userName" property="userName" jdbcType="VARCHAR" />
		    <result column="passWord" property="passWord" jdbcType="VARCHAR" />
		    <result column="user_sex" property="userSex" javaType="com.neo.enums.UserSexEnum"/>
		    <result column="nick_name" property="nickName" jdbcType="VARCHAR" />
	    </resultMap>
	    

	    <sql id="Base_Column_List" >
	    	id, userName, passWord, user_sex, nick_name
	    </sql>
	    
	    <select id="getAll" resultMap="BaseResultMap"  >
	       SELECT 
	       <include refid="Base_Column_List" />
	       FROM users
	    </select>
	    
	    <select id="getOne" parameterType="java.lang.Long" resultMap="BaseResultMap" >
	   	   SELECT 
	       <include refid="Base_Column_List" />
	       FROM users
	       WHERE id = #{id}
	    </select>
	    
	    <insert id="insert" parameterType="com.neo.entity.UserEntity" >
	        INSERT INTO 
		    users
		    (userName,passWord,user_sex) 
		    VALUES
		    (#{userName}, #{passWord}, #{userSex})
	    </insert>
	    
	    <update id="update" parameterType="com.neo.entity.UserEntity" >
	       UPDATE 
		    users 
		       SET 
			    <if test="userName != null">userName = #{userName},</if>
			    <if test="passWord != null">passWord = #{passWord},</if>
			    nick_name = #{nickName}
		       WHERE 
		    id = #{id}
	    </update>
	    
	    <delete id="delete" parameterType="java.lang.Long" >
	       DELETE FROM
		     users 
		       WHERE 
		     id =#{id}
	    </delete>
	</mapper>
	其实就是把上个版本中mapper的sql搬到了这里的xml中了
	3、编写Dao层的代码
	public interface UserMapper {
	    List<UserEntity> getAll();
	    UserEntity getOne(Long id);
	    void insert(UserEntity user);
	    void update(UserEntity user);
	    void delete(Long id);
	}
	对比上一步这里全部只剩了接口方法

 

## idea自动导包
Settings  中 Build Execution Deploy  下的Importing  勾选
Import Maven projects  automatical .....
![](C:\Users\admin\Desktop\图片1.png)



![图片2](C:\Users\admin\Desktop\图片2.png)



## 自动生成getset方法的插件 ##
    <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
    <dependency>
	    <groupId>org.projectlombok</groupId>
	    <artifactId>lombok</artifactId>
	    <version>1.18.4</version>
	    <scope>provided</scope>
    </dependency>
    使用注解
    @Data
    @ToString
    如果没有效果就
    1.File - > setting - > plugins
    2.点击Browse Repositories输入lombok
    3.点击安装
    4.重启IDEA


## 更换连接池 ##
在mybatis包中的将连接池剔除掉
    <!--mybatis-->
    <dependency>
	    <groupId>org.mybatis.spring.boot</groupId>
	    <artifactId>mybatis-spring-boot-starter</artifactId>
	    <version>1.3.2</version>
	    <exclusions>
		    <!--排除数据库连接池-->
		    <exclusion>
			    <groupId>com.zaxxer</groupId>
			    <artifactId>HikariCP</artifactId>
		    </exclusion>
	    </exclusions>
    </dependency>
    
    
    
    在装入druid连接池
    <dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.10</version>
    </dependency>
    直接启动就可以了