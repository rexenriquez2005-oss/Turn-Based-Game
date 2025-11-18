class Assassin extends Hero{
    public Assassin(String name){
        super(name, 150, 120);
        skill = new Skill[]{
            skill[0] = new Skill("Quick Stab", 35 , 30),
        skill[1] = new Skill("Shadow strike",70 , 50),
        skill[2] = new Skill("Deadly Ambush", 90, 100)
        };
    }
    @Override
    public void attack(int skillIndex, Hero target) {
        
        super.attack(skillIndex, target);
    }
     public String[] getSkill(){
        return new String[]{
            skill[1].getSkill_Name(),
            skill[2].getSkill_Name(),
            skill[3].getSkill_Name()
        };
    }
     public Skill[] getSkillsArray() {
        return skill;
    }
}