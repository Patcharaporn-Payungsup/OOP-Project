public class EngineRepairJob extends CarRepairJob {
    private String partName;
    private double partsCost;

    public EngineRepairJob(int jobId, String ownerName, String carPlate, 
                           String severityLevel, String partName, double partsCost) {
        super(jobId, ownerName, carPlate, severityLevel);
        this.partName = partName;
        this.partsCost = partsCost;
    }

    public String getPartName() {
        return partName;
    }

    public double getPartsCost() {
        return partsCost;
    }

    @Override
    public double calculateTotalPrice() {
        return (repairDays * 400.0) + partsCost;
    }

    @Override
    public String toString() {
        return String.format("%s | Engine Repair | Parts Cost: %s (%.2f B.) | Total: %.2f B.\n",
                super.toString(), partName, partsCost, calculateTotalPrice());
    }
}