class Tank extends Hero {
    public Tank(String name) {
        super(name, 300, 100);
        skill = new Skill[]{
            skill[0] = new Skill("Shield Bash", 20, 10),
            skill[1] = new Skill("Ground Slam", 30, 20),
            skill[2] = new Skill("Iron Fist", 40, 30)  
        };
    }

    @Override
    public void attack(int skillIndex, Hero target) {
        super.attack(skillIndex, target); 
    }
    
    public String[] getSkill(){
        return new String[]{
            skill[0].getSkill_Name(),
            skill[1].getSkill_Name(),
            skill[2].getSkill_Name()
        };
    }
    
    public Skill[] getSkillsArray() {
        return skill;
    }
    
    
    public boolean isTank() {
        return true;
    }
}

