public class PaintRepairJob extends CarRepairJob {
    private int paintedColor;

    public PaintRepairJob(int jobId, String ownerName, String carPlate, 
                          String severityLevel, int paintedPieces) {
        super(jobId, ownerName, carPlate, severityLevel);
        this.paintedColor = paintedPieces;
    }

    public int getPaintedPieces() {
        return paintedColor;
    }

    @Override
    public double calculateTotalPrice() {
        return (repairDays * 350.0) + (paintedColor * 600.0);
    }

    @Override
    public String toString() {
        return String.format("%s | Paint Repair | Total: %.2f B.\n",
                super.toString(), calculateTotalPrice());
    }
}