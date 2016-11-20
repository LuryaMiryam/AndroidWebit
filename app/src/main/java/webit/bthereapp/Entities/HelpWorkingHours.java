package webit.bthereapp.Entities;

/**
 * Created by User on 11/05/2016.
 */
public class HelpWorkingHours {
    WorkingHours worker1;
    WorkingHours worker2;

    public HelpWorkingHours(WorkingHours worker, WorkingHours breach) {
        this.worker1 = worker;
        this.worker2 = breach;
    }

    public WorkingHours getWorker1() {
        return worker1;
    }

    public void setWorker1(WorkingHours worker1) {
        this.worker1 = worker1;
    }

    public WorkingHours getWorker2() {
        return worker2;
    }

    public void setWorker2(WorkingHours worker2) {
        this.worker2 = worker2;
    }
}
