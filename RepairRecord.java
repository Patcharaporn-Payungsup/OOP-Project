public interface RepairRecord {
    double calculateTotalPrice();
    String getJobCode();
    @Override
    String toString();
}