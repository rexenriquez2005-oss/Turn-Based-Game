class Mage extends Hero{
    
    public Mage(String name){
        super(name, 120, 200);
        skill = new Skill[]{
            skill[0] = new Skill("FireBall",30,20),
            skill[1] = new Skill("Earthquake", 15 , 20),
            skill[2] = new Skill("Thunder Bolt", 50 , 80)
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