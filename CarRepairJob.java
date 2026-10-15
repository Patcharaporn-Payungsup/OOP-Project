public abstract class CarRepairJob implements RepairRecord {
    protected int jobId;
    protected String ownerName;
    protected String carPlate;
    protected String severityLevel;
    protected int repairDays;

    public CarRepairJob(int jobId, String ownerName, String carPlate, String severityLevel) {
        this.jobId = jobId;
        this.ownerName = ownerName;
        this.carPlate = carPlate;
        this.severityLevel = severityLevel;

        if (severityLevel.equalsIgnoreCase("Light")) {
            this.repairDays = 3;
        } else if (severityLevel.equalsIgnoreCase("Medium")) {
            this.repairDays = 7;
        } else {
            this.repairDays = 14;
        }
    }

    @Override
    public String getJobCode() {
        return "Job 69/" + String.format("%04d", jobId);
    }

    public String getCarPlate() {
        return carPlate;
    }

    @Override
    public abstract double calculateTotalPrice();

    @Override
    public String toString() {
        return String.format("[%s] Car Plate: %s | Owner: %s | Severity: %s (%d Days)",
                getJobCode(), carPlate, ownerName, severityLevel, repairDays);
    }
}