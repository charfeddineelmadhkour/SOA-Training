package metiers;
import entities.Module;
import entities.UniteEnseignement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModuleBusiness {
    private static List<Module> modules;
    private UniteEnseignementBusiness uniteEnseignementBusiness = new UniteEnseignementBusiness();

    static {
        UniteEnseignementBusiness ueBusiness = new UniteEnseignementBusiness();
        modules = new ArrayList<Module>();
        // Initialisation avec quelques données de test
        modules.add(new Module("M101", "Algorithmique", 3, 30, Module.TypeModule.PROFESSIONNEL, ueBusiness.getUEByCode(1)));
        modules.add(new Module("M102", "Base de données", 2, 20, Module.TypeModule.PROFESSIONNEL, ueBusiness.getUEByCode(1)));
        modules.add(new Module("M201", "Communication", 1, 15, Module.TypeModule.TRANSVERSAL, ueBusiness.getUEByCode(2)));
    }

    public ModuleBusiness() {
    }

    // Ajouter un module
    public boolean addModule(Module module) {
        if (module == null || module.getUniteEnseignement() == null) {
            return false;
        }
        int code = module.getUniteEnseignement().getCode();
        UniteEnseignement ue = uniteEnseignementBusiness.getUEByCode(code);
        if (ue != null) {
            module.setUniteEnseignement(ue);
            return modules.add(module);
        }
        return false;
    }

    // Récupérer un module par son matricule
    public Module getModuleByMatricule(String matricule) {
        for (Module m : modules) {
            if (m.getMatricule() != null && m.getMatricule().equals(matricule)) {
                return m;
            }
        }
        return null;
    }

    // Récupérer les modules par type
    public List<Module> getModulesByType(Module.TypeModule type) {
        List<Module> result = new ArrayList<>();
        for (Module m : modules) {
            if (m.getType() == type) {
                result.add(m);
            }
        }
        return result;
    }

    // Mettre à jour un module
    public boolean updateModule(String matricule, Module updatedModule) {
        if (updatedModule == null) {
            return false;
        }
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getMatricule() != null && modules.get(i).getMatricule().equals(matricule)) {
                if (updatedModule.getMatricule() == null || updatedModule.getMatricule().isEmpty()) {
                    updatedModule.setMatricule(matricule);
                }
                if (updatedModule.getUniteEnseignement() != null) {
                    UniteEnseignement ue = uniteEnseignementBusiness.getUEByCode(updatedModule.getUniteEnseignement().getCode());
                    if (ue != null && updatedModule.getUniteEnseignement().getDomaine() == null) {
                        updatedModule.setUniteEnseignement(ue);
                    }
                }
                modules.set(i, updatedModule);
                return true;
            }
        }
        return false;
    }

    // Supprimer un module
    public boolean deleteModule(String matricule) {
        Iterator<Module> iterator = modules.iterator();
        while (iterator.hasNext()) {
            Module m = iterator.next();
            if (m.getMatricule() != null && m.getMatricule().equals(matricule)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    // Récupérer tous les modules
    public List<Module> getAllModules() {
        return modules;
    }

    // Récupérer les modules d'une UE spécifique
    public List<Module> getModulesByUE(UniteEnseignement ue) {
        List<Module> result = new ArrayList<>();
        if (ue == null) {
            return result;
        }
        for (Module m : modules) {
            if (m.getUniteEnseignement() != null &&
                    m.getUniteEnseignement().getCode() == ue.getCode()) {
                result.add(m);
            }
        }
        return result;
    }
}
