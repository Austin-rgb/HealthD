package ochie.healthd;
public interface HealthDCore {
    String login(String email,String password);
    String register(String email,String password);
    int updateProfile(String sessionId,String name,String data);
    int upgradeToDoc(String sessionId, String licenceInfo);
    int addDoc(String sessionId,String requestId);
    int requestPatient(String sessionId,String patientId);
    int addPatientData(String sessionId,String patientId,String data);
    String getPatientData(String sessionId);
    int upgradeToResearcher(String sessionId,String institutionInfo);
    int logout(String sessionId);
    int unregister(String sessionId);
}