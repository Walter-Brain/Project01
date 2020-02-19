package task;

import app.FileMeta;
import dao.FileOperatorDAO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User:S-
 * Date:2020/1/5-16:16
 * Version: 1.0
 **/

public class FileOperateTask implements FileScanCallback {
    @Override
    public void execute(File dir) {
        //文件对比
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            //包装为本地的自定义文件类集合
            List<FileMeta> localMetas = compose(children);
            //数据库文件：jdbc查询实现
            //<根据路径查询路径下的所有文件/文件夹>
            List<FileMeta> metas = FileOperatorDAO.query(dir.getPath());

            //数据库有，本地没有的文件
            for (FileMeta meta : metas) {
                if (!localMetas.contains(meta)) {
                    //如果meta是文件夹，还要删除子文件/子文件夹
                    FileOperatorDAO.delete(meta);
                }
            }

            //本地有，数据库没有的文件
            for (FileMeta localMeta : localMetas) {
                //
                if (!metas.contains(localMeta)) {
                    FileOperatorDAO.insert(localMeta);
                }
            }

        }
    }

    private List<FileMeta> compose(File[] children) {
        List<FileMeta> metas = new ArrayList<>();
        if (children != null) {
            for (File child : children) {
                metas.add(new FileMeta(child));
            }
        }
        return metas;
    }
}
