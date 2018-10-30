
# 发布jar到maven仓库

## 更新项目版本

> 设置`poms`版本，包括多模块项目的灵活方法是 **`Versions Maven Plugin`**

1. 把父模块更新到指定版本号，然后更新子模块，与父模块有相同的版本号

    ```
    mvn versions:set -DnewVersion=0.0.7-RELEASE
    mvn -N versions:update-child-modules  
    ```

    注意，如果子模块的版本号已经与父模块不一致，则先运行后面一条命令统一，在更新父模块版本。


2. 然后提交更新：
    ```
    mvn versions:commit
    ```
    
## 发布到中心仓库

```
mvn clean deploy -X
或
mvn clean deploy -P release
```