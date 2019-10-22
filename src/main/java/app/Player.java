package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

 interface FinalTest {
	public void change(List<Card> handCard);
}
public class Player implements FinalTest
{

	public Choice choice=new Choice();
	public String toString(List<Card> handCard)
	{
		String s=new String();
	 	  for(Card c:handCard)
	 	  {
	 		  switch(c.type)
	 		  {
	 		  case 1:s+='♥';break;	
	 		  case 2:s+='♣';break;
	 		  case 3:s+='♦';break;
	 		  case 4:s+='♠';break;
			  default:
	 		  }
	 		  switch(c.rank)
	 		  {
	 		  case 10:s+='J';break;
	 		  case 11:s+='Q';break;
	 		  case 12:s+='K';break;
	 		  case 13:s+='A';break;
	 		  default:s+=c.rank+1;
	 		  }
	 		  s+=' ';
	 	  }
	 	  return s;
	 }
	public array arr=new array();
	public void arrange(List<Card> handCard)
	{
	   arr.ranknum1.clear();
	   arr.ranknum2.clear();
	   arr.ranknum3.clear();
	   arr.ranknum4.clear();
	   arr.typenum1.clear();
	   arr.typenum2.clear();
	   arr.typenum3.clear();
	   arr.typenum4.clear();
	   for(int i=0;i<handCard.size();i++)
	   {
		   if((i+1)<handCard.size()&&handCard.get(i).rank==handCard.get(i+1).rank)
			   if((i+2)<handCard.size()&&handCard.get(i).rank==handCard.get(i+2).rank)
				   if((i+3)<handCard.size()&&handCard.get(i).rank==handCard.get(i+3).rank)    //四张相同的牌
				   {
					   arr.ranknum4.addAll(handCard.subList(i,i+4));
					   i+=3;
				   }
				   else                                               //三张相同的牌
				   {
					   arr.ranknum3.addAll(handCard.subList(i,i+3));
					   i+=2;
				   }
			   else                                   //两张相同的牌
			   {
				   arr.ranknum2.addAll(handCard.subList(i,i+2));
				   i+=1;
			   }
		   else                                  //没有相同的牌
		   {
			   arr.ranknum1.add(handCard.get(i));
		   }
		}
	   for(int i=0;i<handCard.size();i++)
	   {
		   switch(handCard.get(i).type)
		   {
		   case 1:arr.typenum1.add(handCard.get(i));break;
		   case 2:arr.typenum2.add(handCard.get(i));break;
		   case 3:arr.typenum3.add(handCard.get(i));break;
		   case 4:arr.typenum4.add(handCard.get(i));break;
		   default:
		   }
		}
	}
	  
	public Choice tochoice(List<Card> handCard)
	{
		Choice choice=new Choice();
		for(int i=0;i<3;i++)
		   choice.head.add(handCard.get(i));
		for(int i=3;i<8;i++)
		   choice.mid.add(handCard.get(i));
		for(int i=8;i<13;i++)
		   choice.end.add(handCard.get(i));
		return choice;
	}

	public boolean iscontinuous(List<Card> sub)
	   {
		   if((sub.get(0).rank+4)==sub.get(4).rank)
			   return true;
		   else
			   return false;
	   }

	   
	public void change(List<Card> handCard)
	   {
		   Collections.sort(handCard,new Comparator<Card>()          //从小到大排序
	   {
		   public int compare(Card c1, Card c2)
		   {
			   int i = c1.rank - c2.rank;
			   if(i == 0)
				   return c1.type - c2.type;
			   return i;
		   }
	   });
		   arrange(handCard);        //将牌整理到arr中
		   
		   if(arr.typenum4.size()==13||arr.typenum1.size()==13||arr.typenum2.size()==13||arr.typenum3.size()==13)            //至尊青龙
		   {
			   choice=tochoice(handCard);
			   choice.headType="zhizunqinglong";
			   return;
		   }
		   if(arr.ranknum1.size()==13)                                                     //一条龙
		   {
			   choice=tochoice(handCard);
			   choice.headType="yitiaotong";
			   return;
		   }
		   if(handCard.get(0).rank==10)                                        //十二皇族
		   {
			   choice=tochoice(handCard);
			   choice.headType="shierhuangzu";
			   return;
		   }

		   List<Card> continuous1=new ArrayList<Card>();                 //三同花顺
		   List<Card> continuous2=new ArrayList<Card>();
		   int t=-1,f1=-1;
		   for(int i=0;i<4;i++)
		   {
			   ArrayList<Card> type=new ArrayList<Card>();   
			   for(Card c:handCard)
				   if(c.type==i+1)
					   type.add(c);
			   for(int j=0;type.size()>=5&&j+4<type.size();j++)
				   if(iscontinuous(type.subList(j, j+5)))
				   {
					   continuous1=type.subList(j,j+5);               //一组同花顺
					   t=i;
					   f1=j;
				   }
		   }
		   for(int i=0;(i<t+1)&&(!continuous1.isEmpty());i++)
		   {
			   ArrayList<Card> type=new ArrayList<Card>();   
			   for(Card c:handCard)
				   if(c.type==i+1)
					   type.add(c);
			   for(int j=0;type.size()>=5&&j+4<type.size();j++)
			   {
				   if(i==t)
				   {
					   if(j+4<f1)
						   if(iscontinuous(type.subList(j, j+5)))
							   continuous2=type.subList(j,j+5);
				   }
				   else
				   {
					   if(iscontinuous(type.subList(j, j+5)))
						   continuous2=type.subList(j,j+5);
				   }
					   
			   }
		   }
		   if(!continuous1.isEmpty()&&!continuous2.isEmpty())
		   {
			   List<Card> cards= new ArrayList<Card>();
			   for(Card c:handCard)
				   cards.add(c);
			   cards.removeAll(continuous1);
			   cards.removeAll(continuous2);
			   if((cards.get(0).rank+2)==cards.get(2).rank)                //同花顺
			   {
				   if(continuous1.get(0).rank>continuous2.get(0).rank)
				   {
					   choice.end=continuous1;
					   choice.endType="tonghuashun";
					   choice.mid=continuous2;
					   choice.midType="tonghuashun";	   
					   choice.head=cards;
					   choice.headType="santonghuashun";
					   return;
		 		   }
				   else
				   {
					   choice.end=continuous2;
					   choice.endType="tonghuashun";
					   choice.mid=continuous1;
					   choice.midType="tonghuashun";	   
					   choice.head=cards;  
					   choice.headType="santonghuashun";
					   return;
				   } 
				}
			}
		   
		   if(arr.ranknum4.size()==12)           //三分天下
		   {
			   choice=tochoice(handCard);
			   choice.headType="sanfentianxia";
			   return;
		   }
		   
		   if(handCard.get(0).rank>6)   //全大
		   {
			   choice=tochoice(handCard);
			   choice.headType="quanda";
			   return;
		   }
		   
		   if(handCard.get(12).rank<8)   //全小
		   {
			   choice=tochoice(handCard);
			   choice.headType="quanxiao";
			   return;
		   }
		   
		   if(arr.typenum1.size()+arr.typenum3.size()==13||
				   arr.ranknum1.size()+arr.ranknum3.size()==0)   //凑一色
		   {
			   choice=tochoice(handCard);
			   choice.headType="couyise";
			   return;
		   }
		   
		   if(arr.ranknum3.size()==12||(arr.ranknum3.size()==9&&arr.ranknum4.size()==4))              //四套三条
		   {
			   choice=tochoice(handCard);
			   choice.headType="sitaosantiao";
			   return;
		   }
		   
		   if(arr.ranknum2.size()==10&&arr.ranknum3.size()==3)       //五对三条
		   {
			   choice=tochoice(handCard);
			   choice.headType="wuduisantiao";
			   return;
		   }
		   
		   if(arr.ranknum2.size()==12||(arr.ranknum2.size()==8&&arr.ranknum4.size()==4))              //六对半
		   {
			   choice=tochoice(handCard);
			   choice.headType="liuduiban";
			   return;
		   }
		   
		   List<Card> continuous3=new ArrayList<Card>();
		   List<Card> continuous4=new ArrayList<Card>();
		   List<Card> cards=new ArrayList<Card>();
		   cards.add(handCard.get(0));
		   for(int i=1;i<handCard.size();i++)
		   {
			   if(handCard.get(i).rank!=handCard.get(i-1).rank)
				   cards.add(handCard.get(i));
		   }

		   for(int i=0;i<cards.size()-4;i++)
		   {
			   if(iscontinuous(cards.subList(i, i+5)))
			   {
				   continuous3=cards.subList(i, i+5);
			   }
		   }
		   if(!continuous3.isEmpty())
		   {
			   List<Card> handCard2=new ArrayList<Card>();
			   List<Card> cards2=new ArrayList<Card>();
			   for(Card c:handCard)
				   handCard2.add(c);
			   handCard2.removeAll(continuous3);                          
			   cards2.add(handCard2.get(0));
			   for(int i=1;i<handCard2.size();i++)
			   {
				   if(handCard2.get(i).rank!=handCard2.get(i-1).rank)
					   cards2.add(handCard2.get(i));
			   }
			   for(int i=0;i<cards2.size()-4;i++)
				   if(iscontinuous(cards2.subList(i, i+5)))
					   continuous4=cards2.subList(i, i+5);  
		   }
		   if(!continuous3.isEmpty()&&!continuous4.isEmpty())
		   {
			   List<Card> handCard2 =new ArrayList<Card>();
			   for(Card c:handCard)
				   handCard2.add(c);
			   handCard2.removeAll(continuous3);
			   handCard2.removeAll(continuous4);
			   if(handCard2.get(0).rank+2 == handCard2.get(2).rank)                     //三顺子
			   {
				   choice.end=continuous3;
				   choice.mid=continuous4;
				   choice.head=handCard;
				   choice.headType="sanshunzi";
				   return;
			   }
		   }
		   
		   int typen[]=new int[5];              //三同花
		   typen[1]=arr.typenum1.size();
		   typen[2]=arr.typenum2.size();
		   typen[3]=arr.typenum3.size();
		   typen[4]=arr.typenum4.size();
		   int th1=-1,th2=-1;
		   for(int i=1;i<5;i++)
		   {
			   if(typen[i]>4)
			   {
				   typen[i]-=5;
				   if(th1==-1)
					   th1=i;
				   else
				   {
					   th2=i;
					   break;
				   }
				   if(typen[i]>4)
				   {
					   typen[i]-=5;
					   th2=i;
					   break;
				   }
			   }
		   }
		   for(int i=1;i<5&&th1!=-1&&th2!=-1;i++)
			   if(typen[i]==3)
			   {
				   List<Card> type=new ArrayList<>();
				   switch(i)
				   {
				   case 1:type=arr.typenum1;break;
				   case 2:type=arr.typenum2;break;
				   case 3:type=arr.typenum3;break;
				   case 4:type=arr.typenum4;break;
				   default:
				   }
				   choice.head.addAll(type);
				   switch(th1)
				   {
				   case 1:type=arr.typenum1;break;
				   case 2:type=arr.typenum2;break;
				   case 3:type=arr.typenum3;break;
				   case 4:type=arr.typenum4;break;
				   default:
				   }
				   choice.mid.addAll(type);
				   switch(th2)
				   {
				   case 1:type=arr.typenum1;break;
				   case 2:type=arr.typenum2;break;
				   case 3:type=arr.typenum3;break;
				   case 4:type=arr.typenum4;break;
				   default:
				   }
				   if(choice.mid.get(4).rank>type.get(4).rank)
				   {
					   choice.end.addAll(choice.mid);
					   choice.mid=type;
				   }
				   else
					   choice.end.addAll(type);
				   choice.headType="santonghua";
				   return;
			   }
		   
		   List<Card> card= new ArrayList<Card>();
		   for(Card c:handCard)
			   card.add(c);
		   if(!continuous1.isEmpty())
		   {
			   choice.end=continuous1;                      //尾道为同花顺
			   choice.endType="tonghuashun"; 
			   card.removeAll(continuous1);
			   arrange(card);
			   if(!continuous2.isEmpty())                               //中道为同花顺
			   {
				   if(continuous1.get(4).rank<continuous2.get(4).rank)
				   {
					   choice.end=continuous2;
					   choice.mid=continuous1;
				   }
				   else
					   choice.mid=continuous2;
				   choice.midType="tonghuashun"; 	   
				   card.removeAll(continuous2);
				   choice.head=card;
				   if(card.get(0).rank==card.get(1).rank&&card.get(1).rank==card.get(2).rank)
					   choice.headType="santiao";
				   else if(card.get(0).rank==card.get(1).rank||card.get(1).rank==card.get(2).rank)
					   choice.headType="yidui";
				   choice.headType="wulong";
				   return;
			   }
			   
			   if(arr.ranknum4.size()==8)
			   {
				   choice.head.addAll(card.subList(0, 3));
				   choice.headType="santiao";
				   choice.mid.addAll(card.subList(3, 8));                //中道为铁支，头道为三条
				   choice.midType="tiezhi";
				   return;
			   }
			   if(arr.ranknum4.size()==4)
			   {
				   choice.mid.addAll(arr.ranknum4);
				   card.removeAll(arr.ranknum4);
				   arrange(card);
				   if(!arr.ranknum3.isEmpty())
				   {
					   choice.head.addAll(arr.ranknum3);
					   choice.headType="santiao";                     //中道为铁支，头道为三条
					   card.removeAll(arr.ranknum3);
					   choice.mid.addAll(card);
					   choice.midType="tiezhi";
				   }else if(!arr.ranknum2.isEmpty())                            //中道为铁支，头道为一对
				   {
					   choice.head.addAll(arr.ranknum2.subList(arr.ranknum2.size()-2, arr.ranknum2.size()));
					   card.removeAll(choice.head);
					   choice.mid.add(card.get(0));
					   choice.head.add(card.get(1));
					   choice.headType="yidui";      
					   choice.midType="tiezhi";
				   }
				   else 
				   {                                                       //中道为铁支，头道为乌龙
					   choice.mid.add(card.get(3));
					   choice.midType="tiezhi";
					   choice.head.addAll(card.subList(0, 3));
					   choice.headType="wulong";
				   }
				   return;
			   }
			   
			   if(arr.ranknum3.size()==6)
			   {
				                                                                    //中道为葫芦
				   if(!arr.ranknum2.isEmpty())
				   {
					   choice.mid.addAll(card.subList(3, 6));  
					   choice.mid.addAll(arr.ranknum2);
					   card.removeAll(choice.mid);
					   choice.midType="hulu";
					   choice.head.addAll(card);                                                               //从这里开始不加类型
				   }
				   else
				   {
					   choice.mid.addAll(arr.ranknum3.subList(1, 6));
					   choice.midType="hulu";
					   card.removeAll(choice.mid);
					   choice.head.addAll(card);   
				   }
				   return;
			   }
			   if(arr.ranknum3.size()==3)
			   {
				   if(!arr.ranknum2.isEmpty())
				   {
					   choice.mid.addAll(arr.ranknum3);
					   choice.mid.addAll(arr.ranknum2.subList(0, 2));
					   choice.midType="hulu";
					   card.removeAll(choice.mid);
					   choice.head.addAll(card);   
					   return;
				   }
			   }
			   
			   typen[1]=arr.typenum1.size();
			   typen[2]=arr.typenum2.size();
			   typen[3]=arr.typenum3.size();
			   typen[4]=arr.typenum4.size();
			   for(int i=0;i<5;i++)
			   {
				   List<Card>type=new ArrayList<Card>();
				   if(typen[i]==5)                                    //中道为同花
				   {
					  switch(i)
					  {
					  case 1:type=arr.typenum1;break;
					  case 2:type=arr.typenum2;break;
					  case 3:type=arr.typenum3;break;
					  case 4:type=arr.typenum4;break;
					  default:
					  }
					   choice.mid.addAll(type);
					   card.removeAll(choice.mid);
					   choice.head.addAll(card);
					   choice.midType="tonghua";
					   return;
				   }
				   else if(typen[i]>5)
				   {
					   switch(i)
					   {
						  case 1:type=arr.typenum1;break;
						  case 2:type=arr.typenum2;break;
						  case 3:type=arr.typenum3;break;
						  case 4:type=arr.typenum4;break;
						  default:
					  }
					   if(!arr.ranknum3.isEmpty())
					   {
						   if(type.size()>=5)
						   {
							   choice.mid.clear();
							   choice.mid.add(type.get(type.size()-1));
							   choice.mid.add(type.get(type.size()-2));
							   choice.mid.addAll(type.subList(0, 3));
						   }
						   else
						   {
							   choice.mid.add(type.get(type.size()-1));
							   choice.mid.add(type.get(type.size()-2));
							   choice.mid.addAll(type.subList(0, 3));
						   }
						   card.removeAll(choice.mid);
						   choice.head.addAll(card);
						   choice.midType="tonghua";
						   return;
					   }  
					   else if(arr.ranknum2.size()==4)
					   {
						   type.removeAll(arr.ranknum2.subList(2, 4));
						   choice.mid.addAll(type);
						   card.removeAll(choice.mid);
						   choice.head.addAll(card);
						   choice.midType="tonghua";
						   return;
					   }
					   else if(arr.ranknum2.size()==2)
					   {
						   type.removeAll(arr.ranknum2);
						   choice.mid.add(arr.ranknum2.get(arr.ranknum2.size()-1));
						   choice.mid.addAll(arr.ranknum2.subList(0, 2));
						   card.removeAll(choice.mid);
						   choice.head.addAll(card);
						   choice.midType="tonghua";
						   return;
					   }
					   choice.mid.add(type.get(type.size()-1));
					   choice.mid.addAll(type.subList(0, 3));
					   card.removeAll(choice.mid);
					   choice.head.addAll(card);
					   choice.midType="tonghua";
					   return;
				   }
			   }
			   
			   List<Card> card1= new ArrayList<Card>();
			   card1.add(card.get(0));
			   for(int i=1;i<card.size();i++)
			   {
				   if(card.get(i-1).rank!=card.get(i).rank)
					   card1.add(card.get(i));
			   }
			   for(int i=0;i<card1.size()-4;i++)                               //中道为顺子
				   if(iscontinuous(card1.subList(i, i+5)))
				   {
					   choice.mid=card1.subList(i, i+5);
				   }  
			   if(!choice.mid.isEmpty())
			   {
				   card.removeAll(choice.mid);
				   choice.head.addAll(card);
				   choice.midType="shunzi";
				   return;
			   }
			   
			   if(arr.ranknum3.size()==3)
			   {
				   choice.mid.addAll(arr.ranknum3);
				   choice.mid.add(arr.ranknum1.get(0));
				   choice.mid.add(arr.ranknum1.get(arr.ranknum1.size()-1));
				   card.removeAll(choice.mid);
				   choice.head=card;
				   choice.midType="santiao";
				   return;
			   }
			   
			   if(arr.ranknum2.size()==6)
			   {
				   choice.mid.addAll(arr.ranknum2.subList(4, 6));
				   choice.mid.addAll(arr.ranknum2.subList(0, 2));
				   choice.mid.add(arr.ranknum1.get(0));
				   card.removeAll(choice.mid);
				   choice.head=card;
				   choice.midType="liangdui";
				   choice.headType="yidui";
				   return;
			   }

			   if(arr.ranknum2.size()==4||arr.ranknum2.size()==2)
			   {
				   choice.mid.addAll(arr.ranknum2.subList(arr.ranknum2.size()-2, arr.ranknum2.size()));
				   choice.mid.addAll(arr.ranknum1.subList(0, 3));
				   card.removeAll(choice.mid);
				   choice.head=card;
				   choice.midType="yidui";
				   return;
			   }
			   
			   choice.mid.addAll(card.subList(5, 8));
			   choice.mid.addAll(card.subList(0, 2));
			   card.removeAll(choice.mid);
			   choice.head=card;
			   choice.midType="wulong";
			   return;
		   }
			
		   
		   if(arr.ranknum4.size()==8)                                                       //尾道为铁支
		   {
			   choice.end.addAll(arr.ranknum4.subList(4, 8));
			   choice.mid.addAll(arr.ranknum4.subList(0, 5));
			   card.removeAll(choice.end);
			   card.removeAll(choice.mid);
			   arrange(card);
			   if(!arr.ranknum3.isEmpty())
			   {
				   choice.head=arr.ranknum3;
				   choice.headType="santiao";
				   card.removeAll(choice.head);
				   choice.end.add(card.get(0));
				   choice.mid.add(card.get(1));
				   choice.endType="tiezhi";
				   choice.midType="tiezhi";
				   return;
			   }
			   if(arr.ranknum2.size()==4)
			   {
				   choice.head.addAll(arr.ranknum2.subList(2, 4));
				   card.remove(choice.head);
				   choice.head.add(card.get(2));
				   choice.mid.add(card.get(1));
				   choice.end.add(card.get(0));
				   choice.endType="tiezhi";
				   choice.midType="tiezhi";
				   choice.headType="duizi";
				   return;
			   }
			   if(arr.ranknum2.size()==2)
			   {
				   choice.head.addAll(arr.ranknum2);
				   card.remove(choice.head);
				   choice.head.add(card.get(2));
				   choice.mid.add(card.get(1));
				   choice.end.add(card.get(0));
				   choice.endType="tiezhi";
				   choice.midType="tiezhi";
				   choice.headType="duizi";
				   return;
			   }
			   if(arr.ranknum2.isEmpty())
			   {
				   choice.end.add(card.get(0));
				   choice.mid.add(card.get(1));
				   choice.head.addAll(card.subList(2, 5));
				   choice.endType="tiezhi";
				   choice.midType="tiezhi";
				   choice.headType="wulong";
				   return;
			   }
		   }
		   if(arr.ranknum4.size()==4)                  //尾道为铁支
		   {
			   choice.end.addAll(arr.ranknum4);
			   card.removeAll(choice.end);
			   arrange(card);
			   if(arr.ranknum3.size()==6)              //中道是葫芦
			   {
				   if(!arr.ranknum2.isEmpty())
				   {
					   choice.mid.addAll(arr.ranknum3.subList(3, 6));
					   choice.mid.addAll(arr.ranknum2);
					   choice.midType="hulu";
					   choice.head.addAll(arr.ranknum3.subList(0, 3));
					   choice.headType="santiao";
					   card.removeAll(choice.head);
					   card.removeAll(choice.mid);
					   choice.end.addAll(card);
					   choice.endType="tiezhi";
					   return;
				   }
				   else
				   {
					   choice.mid.addAll(arr.ranknum3.subList(1, 6));
					   card.removeAll(choice.mid);
					   choice.end.add(card.get(0));
					   choice.head.addAll(card.subList(1, 4));
					   choice.endType="tiezhi";
					   choice.midType="hulu";
					   choice.headType="wulong";
					   return;
				   }
			   }
			   if(arr.ranknum3.size()==3)
			   {
				   if(arr.ranknum2.size()==6)
				   {
					   choice.end.add(arr.ranknum2.get(0));
					   choice.mid.addAll(arr.ranknum3);
					   choice.mid.addAll(arr.ranknum2.subList(2, 4));
					   card.removeAll(choice.end);
					   card.removeAll(choice.mid);
					   choice.head=card;
					   choice.endType="tiezhi";
					   choice.midType="hulu";
					   choice.headType="duizi";
					   return;
				   }
				   if(arr.ranknum2.size()==4||arr.ranknum2.size()==2)
				   {
					   choice.end.add(arr.ranknum1.get(0));
					   choice.mid.addAll(arr.ranknum3);
					   choice.mid.addAll(arr.ranknum2.subList(0, 2));
					   card.removeAll(choice.end);
					   card.removeAll(choice.mid);
					   choice.head.addAll(card);
					   choice.endType="tiezhi";
					   choice.midType="hulu";
					   return;
				   }
			   }
			   
			   typen[1]=arr.typenum1.size();
			   typen[2]=arr.typenum2.size();
			   typen[3]=arr.typenum3.size();
			   typen[4]=arr.typenum4.size();
			   for(int i=1;i<5;i++)
			   {
				   if(typen[i]==5)
				   {
					   List<Card>type=new ArrayList<Card>();
					   switch(i)
					   {
					   case 1:type=arr.typenum1;break;
					   case 2:type=arr.typenum2;break;
					   case 3:type=arr.typenum3;break;
					   case 4:type=arr.typenum4;break;
					   default:
					   }
					   choice.mid.addAll(type.subList(0, 5));
					   card.removeAll(choice.mid);
					   arrange(card);
					   if(!arr.ranknum3.isEmpty())
					   {
						   choice.head.addAll(arr.ranknum3);
						   choice.end.addAll(arr.ranknum1);
						   choice.headType="santiao";
					   }
					   if(!arr.ranknum2.isEmpty())
					   {
						   choice.head.addAll(arr.ranknum2.subList(arr.ranknum2.size()-2, arr.ranknum2.size()));
						   card.removeAll(choice.head);
						   choice.head.add(card.get(1));
						   choice.end.add(card.get(0));
						   choice.headType="duizi";
					   }
					   choice.endType="tiezhi";
					   choice.midType="tonghua";
					   return;
				   }
				   if(typen[i]>5)
				   {
					   List<Card>type=new ArrayList<Card>();
					   switch(i)
					   {
					   case 1:type=arr.typenum1;break;
					   case 2:type=arr.typenum2;break;
					   case 3:type=arr.typenum3;break;
					   case 4:type=arr.typenum4;break;
					   default:
					   }
					   if(!arr.ranknum3.isEmpty())
					   {
						   type.removeAll(arr.ranknum3);
						   if(type.size()>=5)
						   {
							   choice.mid.addAll(type.subList(type.size()-5, type.size()));
							   card.removeAll(choice.mid);
							   choice.end.add(card.get(0));
							   choice.head.addAll(card.subList(1, 4));
							   choice.endType="tiezhi";
							   choice.midType="tonghua";
							   return;
						   }
					   }
					   if(arr.ranknum2.size()==6)
					   {
						   type.removeAll(arr.ranknum2.subList(4, 6));
						   choice.mid.addAll(type);
						   card.removeAll(choice.mid);
						   choice.head.addAll(arr.ranknum2.subList(4, 6));
						   card.removeAll(choice.head);
						   choice.head.add(card.get(1));
						   choice.end.add(card.get(0));
						   return;
						   
					   }
					   if(arr.ranknum2.size()==4||arr.ranknum2.size()==2)
					   {
						   choice.head.addAll(arr.ranknum2.subList(arr.ranknum2.size()-2, arr.ranknum2.size()));
						   type.removeAll(choice.head);
						   choice.mid.addAll(type.subList(type.size()-5, type.size()));
						   card.removeAll(choice.head);
						   card.removeAll(choice.mid);
						   choice.head.add(card.get(1));
						   choice.end.add(card.get(0));
						   choice.endType="tiezhi";
						   choice.midType="tonghua";
						   choice.headType="duizi";
						   return;
					   }
					   choice.mid.addAll(arr.ranknum2.subList(arr.ranknum2.size()-3, arr.ranknum2.size()));
					   choice.mid.addAll(arr.ranknum2.subList(0, 2));
					   card.removeAll(choice.mid);
					   choice.end.add(card.get(0));
					   choice.head.addAll(card.subList(1, 4));
					   choice.endType="tiezhi";
					   choice.midType="tonghua";
					   choice.headType="wulong";
					   return;
				   }
			   }
			   
			   List<Card> card1= new ArrayList<Card>();
			   card1.add(card.get(0));
			   for(int i=1;i<card.size();i++)
			   {
				   if(card.get(i-1).rank!=card.get(i).rank)
					   card1.add(card.get(i));
			   }
			   for(int i=0;i<card1.size()-4;i++)                               //中道为顺子  //未考虑顺子包含对子的情况
				   if(iscontinuous(card1.subList(i, i+5)))
				   {
					   choice.mid.clear();
					   choice.mid.addAll(card1.subList(i, i+5));
				   }
			   if(!choice.mid.isEmpty())
			   {
				   card.removeAll(choice.mid);
				   choice.head.addAll(card.subList(1, 4));
				   choice.end.add(card.get(0));
				   choice.midType="shunzi";
				   return;
			   }
			   
			   if(arr.ranknum3.size()==3)
			   {
				   choice.end.add(arr.ranknum1.get(0));
				   choice.mid.addAll(arr.ranknum3);
				   choice.mid.add(arr.ranknum1.get(1));
				   choice.mid.add(arr.ranknum1.get(arr.ranknum1.size()-1));
				   card.removeAll(choice.mid);
				   card.removeAll(choice.end);
				   choice.head.addAll(card);
				   choice.midType="santiao";
				   return;
			   }
			   
			   if(arr.ranknum2.size()==6)
			   {
				   choice.mid.addAll(arr.ranknum2.subList(4, 6));
				   choice.mid.addAll(arr.ranknum2.subList(0, 2));
				   choice.mid.add(arr.ranknum1.get(1));
				   choice.end.add(arr.ranknum1.get(0));
				   card.removeAll(choice.mid);
				   card.removeAll(choice.end);
				   choice.head=card;
				   choice.midType="liangdui";
				   choice.headType="yidui";
				   return;
			   }
			   
			   if(arr.ranknum2.size()==4)
			   {
				   choice.mid.addAll(arr.ranknum2.subList(2, 4));
				   choice.mid.add(arr.ranknum1.get(arr.ranknum1.size()-1));
				   choice.mid.addAll(arr.ranknum1.subList(1, 3));
				   card.removeAll(choice.mid);
				   choice.end.add(arr.ranknum1.get(0));
				   card.removeAll(choice.end);
				   choice.head=card;
				   choice.midType="yidui";
				   choice.headType="yidui";
				   return;
			   }
			   
			   choice.mid.addAll(card.subList(6, 9));
			   choice.mid.addAll(card.subList(1, 3));
			   choice.end.add(card.get(0));
			   card.removeAll(choice.mid);
			   card.removeAll(choice.end);
			   choice.head=card;
			   choice.midType="wulong";
			   choice.headType="wulong";
			   return;  
		   }
		   
		   if(arr.ranknum3.size()==9)                    //尾道是葫芦
		   {
			   if(arr.ranknum2.size()==4)
			   {
				   choice.end.addAll(arr.ranknum3.subList(6, 9));
				   choice.end.addAll(arr.ranknum2.subList(2, 4));
				   choice.mid.addAll(arr.ranknum3.subList(0, 3));
				   choice.end.addAll(arr.ranknum2.subList(0, 2));
				   card.removeAll(choice.mid);
				   card.removeAll(choice.end);
				   choice.head=card;
				   choice.endType="hulu";
				   choice.midType="hulu";
				   return;
			   }
			   if(arr.ranknum2.size()==2)
			   {
				   choice.end.addAll(arr.ranknum3.subList(6, 9));
				   choice.end.addAll(arr.ranknum2);
				   choice.endType="hulu";
				   choice.mid.addAll(arr.ranknum3.subList(1, 6));
				   choice.midType="hulu";
				   card.removeAll(choice.end);
				   card.removeAll(choice.mid);
				   choice.head=card;
				   return;
			   }
		   }
		   if(arr.ranknum3.size()==6&&arr.ranknum2.size()>=4)
		   {
			   choice.end.addAll(arr.ranknum3.subList(3, 6));
			   choice.end.addAll(arr.ranknum2.subList(2, 4));
			   choice.mid.addAll(arr.ranknum3.subList(0, 3));
			   choice.mid.addAll(arr.ranknum2.subList(0, 2));
			   card.removeAll(choice.mid);
			   card.removeAll(choice.end);
			   choice.head=card;
			   choice.endType="hulu";
			   choice.midType="hulu";
			   return; 
		   }
		   if(arr.ranknum3.size()>=3&&!arr.ranknum2.isEmpty())
		   {
			  choice.end.addAll(arr.ranknum3.subList(arr.ranknum3.size()-3, arr.ranknum3.size()));
			  choice.end.addAll(arr.ranknum2.subList(arr.ranknum2.size()-2, arr.ranknum2.size()));
			  choice.endType="hulu";
			  card.removeAll(choice.end);
			  arrange(card);
			   typen[1]=arr.typenum1.size();
			   typen[2]=arr.typenum2.size();
			   typen[3]=arr.typenum3.size();
			   typen[4]=arr.typenum4.size();
			   for(int i=0;i<5;i++)
			   {
				   List<Card>type=new ArrayList<Card>();
				   switch(i)
				   {
				   case 1:type=arr.typenum1;break;
				   case 2:type=arr.typenum2;break;
				   case 3:type=arr.typenum3;break;
				   case 4:type=arr.typenum4;break;
				   default:
				   }
				   if(typen[i]==5)                                    //中道为同花
				   {
					   choice.mid.addAll(type);
					   card.removeAll(choice.mid);
					   choice.head.addAll(card);
					   choice.midType="tonghua";
					   return;
				   }
				   else if(typen[i]>5)
				   {  
					   if(arr.ranknum3.size()==3)
					   {
						   type.removeAll(arr.ranknum3);
						   choice.head.addAll(arr.ranknum3);
						   card.removeAll(arr.ranknum3);
						   choice.mid.addAll(card);
						   choice.midType="tonghua";
						   return;
					   }
					   if(arr.ranknum2.size()==4)
					   {
						   type.removeAll(arr.ranknum2.subList(2, 4));
						   choice.mid.addAll(type);
						   card.removeAll(choice.mid);
						   choice.head.addAll(card);
						   choice.midType="tonghua";
						   return;
					   }
					   else if(arr.ranknum2.size()==2)
					   {
						   type.removeAll(arr.ranknum2);
						   choice.mid.add(type.get(type.size()-1));
						   choice.mid.addAll(type.subList(0, 4));
						   card.removeAll(choice.mid);
						   choice.head.addAll(card);
						   choice.midType="tonghua";
						   return;
					   }
					   choice.mid.add(type.get(type.size()-1));
					   choice.mid.addAll(type.subList(0, 4));
					   card.removeAll(choice.mid);
					   choice.head.addAll(card);
					   choice.midType="tonghua";
					   return;
				   }
				   
			   }
			   
			   List<Card> card1= new ArrayList<Card>();                                     
			   card1.add(card.get(0));
			   for(int i=1;i<card.size();i++)
			   {
				   if(card.get(i-1).rank!=card.get(i).rank)
					   card1.add(card.get(i));
			   }
			   for(int i=0;i<card1.size()-4;i++)                               //中道为顺子
				   if(iscontinuous(card1.subList(i, i+5)))
					   choice.mid=card1.subList(i, i+5);
			   if(!choice.mid.isEmpty())
			   {
				   card.removeAll(choice.mid);
				   choice.head.addAll(card);
				   choice.midType="shunzi";
				   return;
			   }
			   
			   if(arr.ranknum3.size()==3)
			   {
				   choice.mid.addAll(arr.ranknum3);
				   choice.mid.add(arr.ranknum1.get(0));
				   choice.mid.add(arr.ranknum1.get(1));
				   card.removeAll(choice.mid);
				   choice.head.addAll(card);
				   choice.midType="santiao";
				   return;
			   }
			   
			   if(arr.ranknum2.size()==6)
			   {
				   choice.mid.addAll(arr.ranknum2.subList(4, 6));
				   choice.mid.addAll(arr.ranknum2.subList(0, 2));
				   choice.mid.add(arr.ranknum1.get(0));
				   card.removeAll(choice.mid);
				   choice.head.addAll(card);
				   choice.midType="liangdui";
				   choice.headType="yidui";
				   return;
			   }
			   
			   if(arr.ranknum2.size()==4||arr.ranknum2.size()==2)
			   {
				   choice.mid.addAll(arr.ranknum2.subList(arr.ranknum2.size()-2, arr.ranknum2.size()));
				   choice.mid.add(arr.ranknum1.get(arr.ranknum1.size()-1));
				   choice.mid.addAll(arr.ranknum1.subList(0, 2));
				   card.removeAll(choice.mid);
				   choice.head.addAll(card);
				   choice.midType="yidui";
				   return;
			   }
			   choice.mid.addAll(card.subList(5, 8));
			   choice.mid.addAll(card.subList(0, 2));
			   card.removeAll(choice.mid);
			   choice.head.addAll(card);
			   choice.midType="wulong";
			   return;
		   }
		   
		   
		   if(th1!=-1&&th2!=-1)                                         
		   {
			   List<Card>type1=new ArrayList<Card>();
			   List<Card>type2=new ArrayList<Card>();
			   switch(th1)
			   {
			   case 1:type1=arr.typenum1;break;
			   case 2:type1=arr.typenum2;break;
			   case 3:type1=arr.typenum3;break;
			   case 4:type1=arr.typenum4;break;
			   default:
			   }
			   switch(th2)
			   {
			   case 1:type2=arr.typenum1;break;
			   case 2:type2=arr.typenum2;break;
			   case 3:type2=arr.typenum3;break;
			   case 4:type2=arr.typenum4;break;
			   default:
			   }
			   if(type1.get(type1.size()-1).rank>type2.get(type2.size()-1).rank)
			   {
				   choice.end.addAll(type1.subList(type1.size()-5, type1.size()));
				   choice.mid.addAll(type2.subList(type2.size()-5, type2.size()));
			   }
			   else
			   {
				   choice.mid.addAll(type1.subList(type1.size()-5, type1.size()));
				   choice.end.addAll(type2.subList(type2.size()-5, type2.size()));
			   }
			   choice.endType=choice.midType="tonghua";
			   card.removeAll(choice.end);
			   card.removeAll(choice.mid);
			   choice.head.addAll(card);
			   return;
		   }
		   if(th1!=-1)
		   {
			   List<Card>type1=new ArrayList<Card>();
			   switch(th1)
			   {
			   case 1:type1=arr.typenum1;break;
			   case 2:type1=arr.typenum2;break;
			   case 3:type1=arr.typenum3;break;
			   case 4:type1=arr.typenum4;break;
			   default:
			   }
			   if(type1.size()==5)
				   choice.end.addAll(type1);
			   if(type1.size()>5)
			   {
				  if(!arr.ranknum3.isEmpty())  //只可能有一个三条，不可能出现两个
				  {
					  type1.removeAll(arr.ranknum3);
					  choice.end.addAll(type1.subList(0, 4));
					  choice.end.add(type1.get(type1.size()-1));
				  }
				  if(!arr.ranknum2.isEmpty())
				  {
					  type1.removeAll(arr.ranknum2);
					  for(int i=0;type1.size()<5;i++)
					  {
						  if(arr.ranknum2.get(i).type==th1)
							  type1.add(arr.ranknum2.get(i));
					  }
					  choice.end.addAll(type1.subList(type1.size()-5, type1.size()));
				  }
			   }
			   choice.endType="tonghua";
			   card.removeAll(choice.end);
			   arrange(card);
			   List<Card> card2=new ArrayList<Card>();
			   card2.add(card.get(0));
			   for(int i=1;i<card.size();i++)
			   {
				   if(card.get(i).rank!=card.get(i-1).rank)
					   card2.add(card.get(i));
			   }
			   for(int i=0;i<card2.size()-4;i++)                               //中道为顺子  //未考虑顺子包含对子的情况
				   if(iscontinuous(card2.subList(i, i+5)))
					   choice.mid=card2.subList(i, i+5);
			   if(!choice.mid.isEmpty())
			   {
				   card.removeAll(choice.mid);
				   choice.head.addAll(card);                 
				   choice.midType="shunzi";
				   return;
			   }
			   
			   if(arr.ranknum3.size()==3)
			   {
				   choice.mid.addAll(arr.ranknum3);
				   choice.mid.add(arr.ranknum1.get(1));
				   choice.mid.add(arr.ranknum1.get(arr.ranknum1.size()-1));
				   card.removeAll(choice.mid);
				   card.removeAll(choice.end);
				   choice.head=card;
				   choice.midType="santiao";
				   return;
			   }
			   
			   if(arr.ranknum2.size()==6)
			   {
				   choice.mid.addAll(arr.ranknum2.subList(4, 6));
				   choice.mid.addAll(arr.ranknum2.subList(0, 2));
				   choice.mid.add(arr.ranknum1.get(1));
				   card.removeAll(choice.mid);
				   card.removeAll(choice.end);
				   choice.head=card;
				   choice.midType="liangdui";
				   choice.headType="yidui";
				   return;
			   }
			   
			   if(arr.ranknum2.size()==4)
			   {
				   choice.mid.addAll(arr.ranknum2.subList(2, 4));
				   choice.mid.addAll(arr.ranknum1.subList(0, 3));
				   card.removeAll(choice.mid);
				   card.removeAll(choice.end);
				   choice.head=card;
				   choice.midType="yidui";
				   choice.headType="yidui";
				   return;
			   }
			   
			   choice.mid.addAll(card.subList(5, 8));
			   choice.mid.addAll(card.subList(0, 2));
			   card.removeAll(choice.mid);
			   card.removeAll(choice.end);
			   choice.head=card;
			   choice.midType="wulong";
			   choice.headType="wulong";
			   return;  
		   }
		   
		   if(!continuous3.isEmpty()&&!continuous4.isEmpty())
		   {
			   handCard.removeAll(continuous3);
			   handCard.removeAll(continuous4);
			   choice.end=continuous3;
			   choice.mid=continuous4;
			   choice.head=handCard;
			   choice.endType="shunzi";
			   choice.midType="shunzi";
			   choice.headType="wulong";
			   return; 
		   }
		   if(!continuous3.isEmpty())
		   {
			   choice.end.addAll(continuous3);
			   card.removeAll(continuous3);
			   arrange(card);
			   if(arr.ranknum3.size()==3)
			   {
				   choice.mid.addAll(arr.ranknum3);
				   choice.mid.add(arr.ranknum1.get(0));
				   choice.mid.add(arr.ranknum1.get(arr.ranknum1.size()-1));
				   card.removeAll(choice.mid);
				   choice.head=card;
				   choice.midType="santiao";
				   return;
			   }
			   
			   if(arr.ranknum2.size()==6)
			   {
				   choice.mid.addAll(arr.ranknum2.subList(4, 6));
				   choice.mid.addAll(arr.ranknum2.subList(0, 2));
				   choice.mid.add(arr.ranknum1.get(0));
				   card.removeAll(choice.mid);
				   choice.head=card;
				   choice.midType="liangdui";
				   choice.headType="yidui";
				   return;
			   }
			   
			   if(arr.ranknum2.size()==4||arr.ranknum2.size()==2)
			   {
				   choice.mid.addAll(arr.ranknum2.subList(arr.ranknum2.size()-2, arr.ranknum2.size()));
				   choice.mid.add(arr.ranknum1.get(arr.ranknum1.size()-1));
				   choice.mid.addAll(arr.ranknum1.subList(0, 2));
				   card.removeAll(choice.mid);
				   choice.head=card;
				   choice.midType="yidui";
				   return;
			   }
			   
			   choice.mid.addAll(card.subList(5, 8));
			   choice.mid.addAll(card.subList(0, 2));
			   card.removeAll(choice.mid);
			   choice.head=card;
			   choice.midType="wulong";
			   return;
		   }
		   
		   if(!arr.ranknum3.isEmpty())                          //尾道为三条
		   {
			   choice.end.addAll(arr.ranknum3.subList(arr.ranknum3.size()-3, arr.ranknum3.size()));
			   choice.end.addAll(arr.ranknum1.subList(0, 2));
			   choice.endType="santiao";
			   card.removeAll(choice.end);
			   arrange(card);
			   choice.mid.addAll(card.subList(5, 8));
			   choice.mid.addAll(card.subList(0, 2));
			   card.removeAll(choice.mid);
			   choice.head=card;
			   choice.midType="wulong";
			   return;
		   }

		   if(arr.ranknum2.size()>2)
		   {
			   choice.end.addAll(arr.ranknum2.subList(arr.ranknum2.size()-4, arr.ranknum2.size()));
			   choice.end.add(arr.ranknum1.get(0));
			   choice.endType="liangdui";
			   if(arr.ranknum2.size()==10)
			   {
				   choice.mid.addAll(arr.ranknum2.subList(2, 6));
				   choice.mid.add(arr.ranknum1.get(1));
				   choice.midType="liangdui";
			   }
			   else if (arr.ranknum2.size() == 8)
               {
                   choice.mid.addAll(arr.ranknum2.subList(0, 4));
                   choice.mid.add(arr.ranknum1.get(1));
                   choice.midType="liangdui";
               }
			   else if(arr.ranknum2.size()==6)
			   {
				   choice.mid.addAll(arr.ranknum2.subList(0, 2));
				   choice.mid.addAll(arr.ranknum1.subList(1, 4));
				   choice.midType="yidui";
			   }
			   else if(arr.ranknum2.size()==4)
			   {
				   choice.mid.addAll(arr.ranknum1.subList(arr.ranknum1.size()-3, arr.ranknum1.size()));
				   choice.mid.addAll(arr.ranknum1.subList(0, 2));
				   choice.midType="wulong";
			   }
//               System.out.println(toString(choice.mid));
//               System.out.println(toString(choice.end));
//			   card.removeAll(choice.end);
//			   card.removeAll(choice.mid);
//			   choice.head.addAll(card);
//
//
//               System.out.println(choice.headType);
//               System.out.println(choice.midType);
//               System.out.println(choice.endType);
			   return;
		   }
	   }
	
}
