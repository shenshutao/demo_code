<?php

namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use AppBundle\Entity\Commodity;

class ShopController extends Controller
{
    /**
     * @Route("/shop", name="shop")
     */
    public function indexAction(Request $request)
    {
		$repository = $this->getDoctrine()->getRepository(Commodity::class);
    	$items = $repository->findAll();
    	
    	$total_quantity = 0;
        $shopping_cart = $_SESSION['shopping_cart'];
        if(!empty($shopping_cart)) {
	        foreach ($shopping_cart as $key => $value) {
	            $total_quantity += $value;
	        }
   		}
        
        return $this->render('shop/shoppingpage.html.twig', [
            'total_quantity' => $total_quantity,
            'shopping_cart' => $shopping_cart,
            'items' => $items,
        ]);
    }
}
