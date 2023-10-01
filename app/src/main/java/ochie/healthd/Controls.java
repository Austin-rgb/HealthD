package ochie.healthd;

import ochie.healthd.Model;
import ochie.healthd.Util;

public class Controls implements HealthDCore {
  Model model;

  public Controls(String url, String username, String password) {
    model = new Model(url, username, password);
  }

  public String login(String email, String password) {
    if (model.matchCredentials(email, Util.hasher(password))) {
      String sessionId = Util.randomString(48);
      model.saveSession(sessionId, email);
      return sessionId;
    }
    ;
    return "1";
  }

  public String register(String email, String password) {
    if (model.saveCredentials(email, password)) {
      String sessionId = Util.randomString(48);
      model.saveSession(sessionId, email);
      return sessionId;
    }
    return "1";
  }

  public int updateProfile(String sessionId, String name, String data) {
    String email = model.confirmSession(Util.hasher(sessionId));
    if (email.length() > 3) {
    if(model.updateProfile(email,name,data))return  0;
      return 2;
    }
    return 1;
  }

  public int upgradeToDoc(String sessionId, String licenceInfo) {
    String email = model.confirmSession(Util.hasher(sessionId));
    if (email.length() > 3) {
      return 0;
    }
    return 1;
  }

  public int addDoc(String sessionId, String requestId) {
    String email = model.confirmSession(Util.hasher(sessionId));
    if (email.length() > 3) {
      return 0;
    }
    return 1;
  }

  public int requestPatient(String sessionId, String patientId) {
    String email = model.confirmSession(Util.hasher(sessionId));
    if (email.length() > 3) {
      return 0;
    }
    return 1;
  }

  public int addPatientData(String sessionId, String patientId, String data) {
    String email = model.confirmSession(Util.hasher(sessionId));
    if (email.length() > 3) {
      return 0;
    }
    return 1;
  }

  public String getPatientData(String sessionId) {
    String email = model.confirmSession(Util.hasher(sessionId));
    if (email.length() > 3) {
    return  model.getPatientData (email);
    }
    return "1";
  }

  public int upgradeToResearcher(String sessionId, String institutionInfo) {
    String email = model.confirmSession(Util.hasher(sessionId));
    if (email.length() > 3) {
      return 0;
    }
    return 1;
  }

  public int logout(String sessionId) {
    String email = model.confirmSession(Util.hasher(sessionId));
    if (email.length() > 3) {
    model.deactivateSession(email);
      return 0;
    }
    return 1;
  }

  public int unregister(String sessionId) {
    String email = model.confirmSession(Util.hasher(sessionId));
    if (email.length() > 3) {
    model.deactivatePatient(email)
      return 0;
    }
    return 1;
  }
}
