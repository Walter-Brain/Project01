package util;

public class TestCallback {
    public static void main(String[] args) {
        TTT t = new TTT(){

            @Override
            public void execute() {
                System.out.println("插入数据库");
            }
        };
        TestTask task = new TestTask(t);
        task.scanTask();
    }
    public static class TestTask{
        private TTT t;
        public TestTask(TTT t) {
            this.t = t;
        }

        public void scanTask(){
            // 逻辑
            System.out.println("before");
            // 数据库操作
            t.execute();
            // 逻辑
            System.out.println("after");
        }
    }
}
interface TTT{
    void execute();
}
